package com.contacts_list.data

import com.contacts_list.data.local.ContactsListLocalDataSource
import com.contacts_list.data.utils.toDomain
import com.contacts_list.domain.models.Contact
import com.contacts_list.domain.repository.ContactsListRepository

class ContactsListRepositoryImpl(
    private val contactsListLocalDataSource: ContactsListLocalDataSource
) : ContactsListRepository {

    override suspend fun getContacts(): Map<String, List<Contact>> {
        return contactsListLocalDataSource.getContacts()
            .map { it.toDomain() }
            .groupBy { contact ->
                contact.name.first().toString()
            }
            .toSortedMap()
    }
}