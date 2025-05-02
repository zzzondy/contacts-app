package com.contacts_list.presentation.screens.contacts_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.contacts_list.domain.models.Contact
import com.contactsapp.ui.theme.ContactsAppTheme

@Composable
internal fun ContactsListScreenContentState(
    contacts: Map<String, List<Contact>>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        contacts.map { entry ->
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(
                            start = ContactsAppTheme.paddings.medium,
                            top = ContactsAppTheme.paddings.small,
                            bottom = ContactsAppTheme.paddings.small
                        )
                ) {
                    Text(
                        text = entry.key,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            items(entry.value.size) { index ->  

            }
        }
    }
}