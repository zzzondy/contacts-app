package com.contacts_list.data.local

import com.contacts_list.data.local.model.ContactDto

interface ContactsListLocalDataSource {

    suspend fun getContacts(): List<ContactDto>
}