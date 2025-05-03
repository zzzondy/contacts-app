package com.contacts_list.presentation.screens.contacts_list

import android.Manifest
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListAction
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListState
import com.contactsapp.permissions.utils.checkPermission

@Composable
fun ContactsListScreen(
    navController: NavController,
    contactsListScreenViewModel: ContactsListScreenViewModel,
) {
    val context = LocalContext.current
    val hasContactsPermission by rememberSaveable {
        mutableStateOf(context.checkPermission(Manifest.permission.READ_CONTACTS))
    }

    val state by contactsListScreenViewModel.state.collectAsState()

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

@Composable
fun ContactsListScreenContent(
    state: ContactsListState,
    onAction: (ContactsListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        when (state) {
            is ContactsListState.NoContactsPermission -> {
                ContactsListScreenNoPermissionState(
                    isRationaleShowLastPermissionDialog = state.isRationaleShowLastPermissionDialog,
                    onAction = onAction,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is ContactsListState.Content -> {
                ContactsListScreenContentState(
                    contacts = state.contacts,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            ContactsListState.Loading -> {}
        }
    }
}