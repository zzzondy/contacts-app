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
                if (contact.name.isBlank()) {
                    "#"
                } else {
                    if (contact.name.first() == 'ё' || contact.name.first() == 'Ё') {
                        "Ё"
                    } else {
                        contact.name.first().uppercaseChar()
                            .toString()
                    }
                }
            }
            .toSortedMap(groupComparator)
    }

    private val groupComparator = compareBy<String> { groupKey ->
        when {
            groupKey[0] in 'А'..'Е' -> 0
            groupKey[0] == 'Ё' -> 1
            groupKey[0] in 'Ж'..'Я' -> 2
            groupKey[0] in 'A'..'Z' -> 3
            else -> 4
        }
    }.thenBy {
        it
    }

    private val contactComparator = compareBy<Contact> { contact ->
        val firstChar = if (contact.name.isBlank()) {
            '#'
        } else {
            if (contact.name.first() == 'ё' || contact.name.first() == 'Ё') {
                'Ё'
            } else {
                contact.name.first().uppercaseChar()
                    .toString()
            }
        }
        when (firstChar) {
            in 'А'..'Е' -> 0
            'Ё' -> 1
            in 'Ж'..'Я' -> 2
            in 'A'..'Z' -> 3
            else -> 4
        }
    }.thenBy { it.name.lowercase() }
}