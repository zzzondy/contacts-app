package com.contactsapp.permissions

import android.app.Activity
import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.core.app.ActivityCompat
import com.contactsapp.permissions.data.PermissionsRepositoryImpl
import com.contactsapp.permissions.data.local.PermissionsLocalDataSource
import com.contactsapp.permissions.data.local.PermissionsLocalDataSourceImpl
import com.contactsapp.permissions.domain.PermissionsRepository
import com.contactsapp.permissions.utils.checkPermission
import com.contactsapp.permissions.utils.openSettings
import com.contactsapp.permissions.utils.shouldShowRequestPermissionRationale
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class PermissionManager(
    context: Context,
    private val permission: String,
    @StringRes private val dialogTitleResId: Int,
    @StringRes private val dialogTextResId: Int,
) {

    private var onPermissionGranted: () -> Unit = {}

    fun setOnPermissionGranted(onPermissionGranted: () -> Unit) {
        this.onPermissionGranted = onPermissionGranted
    }

    private val permissionsLocalDataSource: PermissionsLocalDataSource =
        PermissionsLocalDataSourceImpl(context)

    private val permissionsRepository: PermissionsRepository =
        PermissionsRepositoryImpl(permissionsLocalDataSource)

    private val coroutineScope = CoroutineScope(SupervisorJob())

    @Composable
    fun rememberRequestPermission(): (String) -> Unit {
        val context = LocalContext.current
        val permissionStatus = remember { mutableStateOf(context.checkPermission(permission)) }

        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            permissionStatus.value = isGranted
            if (isGranted) {
                coroutineScope.launch(Dispatchers.IO) {
                    permissionsRepository.updatePermissionDialogFlag(permission, false)
                }
                onPermissionGranted()
            } else {
                coroutineScope.launch(Dispatchers.IO) {
                    if ((ActivityCompat.shouldShowRequestPermissionRationale(
                            context as Activity,
                            permission
                        ).not() && permissionsRepository.getPermissionDialogFlag(permission).not())
                    ) {
                        permissionsRepository.updatePermissionDialogFlag(permission, true)
                    }
                }
            }
        }

        var isPermissionDialogVisible by rememberSaveable {
            mutableStateOf(false)
        }

        var isLastPermissionDialogVisible by rememberSaveable {
            mutableStateOf(false)
        }

        if (isPermissionDialogVisible) {
            PermissionExplanationDialog(
                onDismiss = { isPermissionDialogVisible = false },
                onConfirm = {
                    isPermissionDialogVisible = false
                    launcher.launch(permission)
                },
                text = stringResource(id = dialogTextResId),
                title = stringResource(id = dialogTitleResId),
                modifier = Modifier.testTag(FIRST_PERMISSION_DIALOG)
            )
        }

        if (isLastPermissionDialogVisible) {
            PermissionExplanationDialog(
                onDismiss = { isLastPermissionDialogVisible = false },
                onConfirm = {
                    isPermissionDialogVisible = false
                    context.openSettings()
                },
                text = stringResource(id = R.string.open_settings),
                title = stringResource(id = dialogTitleResId),
                modifier = Modifier.testTag(SECOND_PERMISSION_DIALOG)
            )
        }

        return { permission ->
            coroutineScope.launch(Dispatchers.IO) {
                val lastPermissionDialogFlag =
                    permissionsRepository.getPermissionDialogFlag(permission)

                if (context.checkPermission(permission)) {
                    permissionsRepository.updatePermissionDialogFlag(permission, false)
                    onPermissionGranted()
                } else if (context.shouldShowRequestPermissionRationale(permission)) {
                    isPermissionDialogVisible = true
                } else if (lastPermissionDialogFlag) {
                    isLastPermissionDialogVisible = true
                } else {
                    launcher.launch(permission)
                }
            }
        }
    }
}