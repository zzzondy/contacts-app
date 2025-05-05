package com.contacts_list.presentation.screens.contacts_list.states

import com.contacts_list.domain.models.Contact

sealed class ContactsListState {

    data class Content(val contacts: Map<String, List<Contact>>) : ContactsListState()

    data object NoContacts : ContactsListState()

    data object NoContactsPermission : ContactsListState()

    data object Loading : ContactsListState()
}