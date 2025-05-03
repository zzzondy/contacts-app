package com.contacts_list.presentation.screens.contacts_list

import androidx.lifecycle.viewModelScope
import com.contacts_list.domain.repository.ContactsListRepository
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListAction
import com.contacts_list.presentation.screens.contacts_list.states.ContactsListState
import com.contactsapp.ui.state_hoisting.StatefulViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactsListScreenViewModel(
    private val contactsListRepository: ContactsListRepository
) :
    StatefulViewModel<ContactsListState, Any, ContactsListAction>() {

    val state = _state.receiveAsFlow()
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ContactsListState.Loading
        )

    override fun onAction(action: ContactsListAction) {
        when (action) {
            is ContactsListAction.EnterScreenWithoutContactsPermission -> {
                onEnterScreenWithoutContactsPermission()
            }

            is ContactsListAction.GivePermissionClicked -> {

            }

            ContactsListAction.ContactsPermissionGranted -> TODO()
            ContactsListAction.ContactsPermissionLastDialogIsRationaleToShow -> TODO()
            ContactsListAction.EnterScreenWithContactsPermission -> TODO()
        }
    }

    private fun onEnterScreenWithoutContactsPermission() {
        viewModelScope.launch {
            updateState(ContactsListState.NoContactsPermission(false))
        }
    }
}