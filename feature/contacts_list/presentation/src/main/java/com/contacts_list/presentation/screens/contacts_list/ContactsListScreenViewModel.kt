package com.contacts_list.presentation.screens.contacts_list

import androidx.lifecycle.viewModelScope
import com.contacts_list.domain.repository.ContactsListRepository
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListAction
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListEffect
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListState
import com.contactsapp.ui.state_hoisting.StatefulViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactsListScreenViewModel(
    private val contactsListRepository: ContactsListRepository,
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
            is ContactsListAction.OnCallButtonClicked -> onCallButtonClicked(action.phoneNumber)
            is ContactsListAction.OnMessageButtonClicked -> onMessageButtonClicked(action.phoneNumber)
        }
    }

    private fun onEnterScreenWithoutContactsPermission() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState(
                ContactsListState.NoContactsPermission
            )
        }
    }

    private fun onEnterScreenWithContactsPermission() {
        if (state.value !is ContactsListState.NoContacts && state.value !is ContactsListState.Content) {
            onContactsPermissionGranted()
        }
    }

    private fun onContactsPermissionGranted() {
        viewModelScope.launch(Dispatchers.IO) {
            updateState(ContactsListState.Loading)
            val contacts = contactsListRepository.getContacts()
            if (contacts.isEmpty()) {
                updateState(ContactsListState.NoContacts)
            } else {
                updateState(ContactsListState.Content(contacts = contactsListRepository.getContacts()))
            }
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