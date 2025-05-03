package com.contacts_list.presentation.screens.contacts_list.states

sealed interface ContactsListAction {

    data object EnterScreenWithoutContactsPermission : ContactsListAction

    data object EnterScreenWithContactsPermission : ContactsListAction

    data object GivePermissionClicked : ContactsListAction

    data object ContactsPermissionGranted : ContactsListAction

    data object ContactsPermissionLastDialogIsRationaleToShow : ContactsListAction
}