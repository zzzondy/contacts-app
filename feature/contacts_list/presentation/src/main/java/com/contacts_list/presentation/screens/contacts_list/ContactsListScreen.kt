package com.contacts_list.presentation.screens.contacts_list

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.contacts_list.presentation.R
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListAction
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListEffect
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListState
import com.contactsapp.permissions.PermissionManager
import com.contactsapp.permissions.utils.checkPermission
import com.contactsapp.ui.utils.collectAsEffect

@Composable
fun ContactsListScreen(
    contactsListScreenViewModel: ContactsListScreenViewModel,
) {
    val context = LocalContext.current
    val hasContactsPermission by rememberSaveable {
        mutableStateOf(context.checkPermission(Manifest.permission.READ_CONTACTS))
    }

    val permissionManager = remember {
        PermissionManager(
            context = context,
            permission = Manifest.permission.CALL_PHONE,
            dialogTitleResId = R.string.no_call_permission,
            dialogTextResId = R.string.please_give_call_permission,
        )
    }
    val requestPermission = permissionManager.rememberRequestPermission()

    val state by contactsListScreenViewModel.state.collectAsState()
    contactsListScreenViewModel.effect.collectAsEffect { effect ->
        when (effect) {
            is ContactsListEffect.CallContact -> {
                permissionManager.setOnPermissionGranted { context.startActivity(
                    Intent(
                        Intent.ACTION_CALL,
                        Uri.parse(TEL + effect.phoneNumber)
                    )
                ) }
                requestPermission(Manifest.permission.CALL_PHONE)
            }

            is ContactsListEffect.MessageContact -> {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(SMS + effect.phoneNumber)
                    )
                )
            }
        }
    }

    LaunchedEffect(hasContactsPermission) {
        if (!hasContactsPermission) {
            contactsListScreenViewModel.onAction(ContactsListAction.EnterScreenWithoutContactsPermission)
        } else {
            contactsListScreenViewModel.onAction(ContactsListAction.EnterScreenWithContactsPermission)
        }
    }

    ContactsListScreenContent(
        state = state,
        onAction = contactsListScreenViewModel::onAction,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContactsListScreenContent(
    state: ContactsListState,
    onAction: (ContactsListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.contacts))
                }
            )
        }
    ) { paddingValues ->
        when (state) {
            is ContactsListState.NoContactsPermission -> {
                ContactsListScreenNoPermissionState(
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is ContactsListState.Content -> {
                ContactsListScreenContentState(
                    onAction = onAction,
                    contacts = state.contacts,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is ContactsListState.Loading -> {
                ContactsListScreenLoadingState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is ContactsListState.NoContacts -> {
                ContactsListScreenNoContactsState(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}

const val SMS = "sms:"
const val TEL = "tel:"