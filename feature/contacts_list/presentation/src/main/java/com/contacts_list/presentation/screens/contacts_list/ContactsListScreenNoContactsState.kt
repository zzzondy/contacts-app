package com.contacts_list.presentation.screens.contacts_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.contacts_list.presentation.R
import com.contactsapp.ui.theme.ContactsAppTheme

@Composable
fun ContactsListScreenNoContactsState(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(horizontal = ContactsAppTheme.paddings.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        userScrollEnabled = false,
    ) {
        item {
            Image(
                imageVector = Icons.Rounded.Person,
                contentDescription = stringResource(id = R.string.no_contacts),
                modifier = Modifier.size(ContactsAppTheme.sizes.extraLarge),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.medium))
        }

        item {
            Text(
                text = stringResource(id = R.string.you_have_not_contacts),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.small))
        }

        item {
            Text(
                text = stringResource(id = R.string.add_contacts_in_app),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}