package com.contacts_list.presentation.screens.contacts_list

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.contacts_list.domain.models.Contact
import com.contacts_list.presentation.R
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
                ContactListItem(contact = entry.value[index])
            }
        }
    }
}

@Composable
private fun ContactListItem(contact: Contact) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(end = ContactsAppTheme.paddings.small)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = contact.photoUri,
                error = painterResource(R.drawable.round_person)
            ),
            contentDescription = stringResource(id = R.string.contact_photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(ContactsAppTheme.paddings.small)
                .size(60.dp)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.weight(1f, true)) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.titleSmall,
            )

            Text(
                text = contact.phoneNumber,
                style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(
            onClick = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse(TEL + contact.phoneNumber))
                context.startActivity(intent)
            },
        ) {
            Image(
                imageVector = Icons.Rounded.Call,
                contentDescription = stringResource(id = R.string.call_your_contact),
                modifier = Modifier.padding(ContactsAppTheme.paddings.small),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }

        IconButton(
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SMS + contact.phoneNumber))
                context.startActivity(intent)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_message),
                contentDescription = stringResource(R.string.message_your_contact),
                modifier = Modifier.padding(ContactsAppTheme.paddings.small),
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

private const val SMS = "sms:"
private const val TEL = "tel:"