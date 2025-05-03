package com.contacts_list.presentation.screens.contacts_list.states

sealed interface ContactsListAction {

    data object EnterScreenWithoutContactsPermission : ContactsListAction

    data object EnterScreenWithContactsPermission : ContactsListAction

    data object ContactsPermissionGranted : ContactsListAction

    data object ContactsPermissionLastDialogIsRationaleToShow : ContactsListAction

    data class OnCallButtonClicked(val phoneNumber: String) : ContactsListAction

    data class OnMessageButtonClicked(val phoneNumber: String) : ContactsListAction
}