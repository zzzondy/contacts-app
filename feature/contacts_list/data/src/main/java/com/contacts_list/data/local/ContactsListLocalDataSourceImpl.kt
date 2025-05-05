package com.contacts_list.data.local

import android.content.Context
import android.provider.ContactsContract
import com.contacts_list.data.local.model.ContactDto

class ContactsListLocalDataSourceImpl(private val context: Context) : ContactsListLocalDataSource {

    override suspend fun getContacts(): List<ContactDto> {
        val contacts = mutableListOf<ContactDto>()

        context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )?.use { contactsCursor ->
            val idIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val photoUriIndex =
                contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI)

            while (contactsCursor.moveToNext()) {
                val id = contactsCursor.getString(idIndex)
                val name = contactsCursor.getString(nameIndex)
                val number = contactsCursor.getString(numberIndex)
                val photoUri = contactsCursor.getString(photoUriIndex)
                contacts.add(
                    ContactDto(
                        id = id, name = name, phoneNumber = number, photoUri = photoUri
                    )
                )
            }
        }

        return contacts
    }
}