package com.contacts_list.presentation.screens.contacts_list.states

sealed interface ContactsListEffect {

    data class CallContact(val phoneNumber: String) : ContactsListEffect

    data class MessageContact(val phoneNumber: String) : ContactsListEffect
}