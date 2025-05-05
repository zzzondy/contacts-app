package com.contacts_list.domain.repository

import com.contacts_list.domain.models.Contact

interface ContactsListRepository {

    suspend fun getContacts(): Map<String, List<Contact>>
}