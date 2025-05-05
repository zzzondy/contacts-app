package com.contacts_list.presentation.screens.contacts_list

import android.Manifest
import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import com.contacts_list.presentation.R
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListAction
import com.contactsapp.permissions.PermissionManager
import com.contactsapp.permissions.utils.checkPermission
import com.contactsapp.permissions.utils.openSettings
import com.contactsapp.permissions.utils.shouldShowRequestPermissionRationale
import com.contactsapp.ui.theme.ContactsAppTheme

@Composable
internal fun ContactsListScreenNoPermissionState(
    onAction: (ContactsListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val permissionManager = remember {
        PermissionManager(
            context = context,
            permission = Manifest.permission.READ_CONTACTS,
            dialogTitleResId = R.string.no_read_contacts_permission,
            dialogTextResId = R.string.please_give_read_contacts_permission,
        ).apply {
            setOnPermissionGranted { onAction(ContactsListAction.ContactsPermissionGranted) }
        }
    }
    val requestPermission = permissionManager.rememberRequestPermission()

    LazyColumn(
        modifier = modifier.padding(horizontal = ContactsAppTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        userScrollEnabled = false
    ) {
        item {
            Image(
                imageVector = Icons.Rounded.Lock,
                contentDescription = stringResource(id = R.string.no_read_contacts_permission),
                modifier = Modifier.size(ContactsAppTheme.sizes.extraLarge),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.medium))
        }

        item {
            Text(
                text = stringResource(id = R.string.no_read_contacts_permission),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.small))
        }

        item {
            Text(
                text = stringResource(id = R.string.please_give_read_contacts_permission),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.large))
        }

        item {
            Button(
                onClick = {
                    requestPermission(Manifest.permission.READ_CONTACTS)
                },
                modifier = Modifier.testTag(GIVE_PERMISSION_BUTTON)
            ) {
                Text(text = stringResource(id = R.string.give_permission))
            }
        }
    }
}

const val GIVE_PERMISSION_BUTTON = "GIVE_PERMISSION_BUTTON"
const val FIRST_PERMISSION_DIALOG = "FIRST_PERMISSION_DIALOG"
const val SECOND_PERMISSION_DIALOG = "SECOND_PERMISSION_DIALOG"
const val CONFIRM_DIALOG_BUTTON = "CONFIRM_DIALOG_BUTTON"