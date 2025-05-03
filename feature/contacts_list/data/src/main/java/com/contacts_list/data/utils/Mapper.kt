package com.contacts_list.data.utils

import com.contacts_list.data.local.model.ContactDto
import com.contacts_list.domain.models.Contact

fun ContactDto.toDomain(): Contact = Contact(
    id, name, phoneNumber, photoUri
)