package com.contacts_list.presentation.screens.contacts_list.states

sealed interface ContactsListAction {

    data object EnterScreenWithoutContactsPermission : ContactsListAction
}