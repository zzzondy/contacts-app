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
            .sortedWith(contactComparator)
            .groupBy { contact ->
                contact.name.firstOrNull()?.uppercaseChar()?.toString() ?: "#"
            }
            .toSortedMap(groupComparator)
    }

    private val groupComparator = compareBy<String> { groupKey ->
        when {
            groupKey[0] in 'А'..'Я' -> 0
            groupKey[0] in 'а'..'я' -> 0
            groupKey[0] in 'A'..'Z' -> 1
            groupKey[0] in 'a'..'z' -> 1
            else -> 2
        }
    }.thenBy { it }

    private val contactComparator = compareBy<Contact> { contact ->
        val firstChar = contact.name.firstOrNull() ?: '#'
        when (firstChar) {
            in 'А'..'Я', in 'а'..'я' -> 0
            in 'A'..'Z', in 'a'..'z' -> 1
            else -> 2
        }
    }.thenBy { it.name.lowercase() }
}