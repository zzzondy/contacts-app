package com.contacts_list.domain.models

data class Contact(
    val name: String,
    val phoneNumber: String,
    val photoUri: String?
)