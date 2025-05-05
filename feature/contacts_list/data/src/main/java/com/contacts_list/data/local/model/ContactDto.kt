package com.contacts_list.data.local.model

data class ContactDto(
    val id: String,
    val name: String,
    val phoneNumber: String,
    val photoUri: String?
)
