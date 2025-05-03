package com.contacts_list.presentation.screens.contacts_list

import android.Manifest
import androidx.lifecycle.viewModelScope
import com.contacts_list.domain.repository.ContactsListRepository
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListAction
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListEffect
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListState
import com.contactsapp.permissions.domain.PermissionsRepository
import com.contactsapp.ui.state_hoisting.StatefulViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactsListScreenViewModel(
    private val contactsListRepository: ContactsListRepository,
    private val permissionsRepository: PermissionsRepository
) :
    StatefulViewModel<ContactsListState, ContactsListEffect, ContactsListAction>() {

    val state = _state.receiveAsFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ContactsListState.Loading
        )

    override fun onAction(action: ContactsListAction) {
        when (action) {
            is ContactsListAction.EnterScreenWithoutContactsPermission -> onEnterScreenWithoutContactsPermission()
            is ContactsListAction.EnterScreenWithContactsPermission -> onEnterScreenWithContactsPermission()
            is ContactsListAction.ContactsPermissionGranted -> onContactsPermissionGranted()
            is ContactsListAction.ContactsPermissionLastDialogIsRationaleToShow -> onContactsPermissionLastDialogIsRationaleToShow()
            is ContactsListAction.OnCallButtonClicked -> onCallButtonClicked(action.phoneNumber)
            is ContactsListAction.OnMessageButtonClicked -> onMessageButtonClicked(action.phoneNumber)
        }
    }

    private fun onEnterScreenWithoutContactsPermission() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState(
                ContactsListState.NoContactsPermission(
                    permissionsRepository.getPermissionDialogFlag(
                        Manifest.permission.READ_CONTACTS
                    )
                )
            )
        }
    }

    private fun onEnterScreenWithContactsPermission() {
        viewModelScope.launch(Dispatchers.IO) {
            permissionsRepository.updatePermissionDialogFlag(
                Manifest.permission.READ_CONTACTS,
                false
            )
        }
        onContactsPermissionGranted()
    }

    private fun onContactsPermissionGranted() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState(ContactsListState.Loading)
            updateState(ContactsListState.Content(contacts = contactsListRepository.getContacts()))
        }
    }

    private fun onContactsPermissionLastDialogIsRationaleToShow() {
        viewModelScope.launch(Dispatchers.IO) {
            permissionsRepository.updatePermissionDialogFlag(
                Manifest.permission.READ_CONTACTS,
                true
            )
            updateState(ContactsListState.NoContactsPermission(true))
        }
    }

    private fun onCallButtonClicked(phoneNumber: String) {
        viewModelScope.launch {
            updateEffect(ContactsListEffect.CallContact(phoneNumber))
        }
    }

    private fun onMessageButtonClicked(phoneNumber: String) {
        viewModelScope.launch {
            updateEffect(ContactsListEffect.MessageContact(phoneNumber))
        }
    }
}