package com.contacts_list.presentation.screens.contacts_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.contacts_list.domain.models.Contact
import com.contacts_list.presentation.R
import com.contactsapp.ui.theme.ContactsAppTheme
import kotlinx.coroutines.launch

@Composable
internal fun ContactsListScreenContentState(
    onCallButtonClicked: (String) -> Unit,
    onMessageButtonClicked: (String) -> Unit,
    contacts: Map<String, List<Contact>>,
    modifier: Modifier = Modifier
) {
    val letters = contacts.keys
    val listState = rememberLazyListState()
    var selectedLetter by remember { mutableStateOf(letters.firstOrNull() ?: "") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val firstVisibleItem = visibleItems.firstOrNull()
                if (firstVisibleItem != null) {
                    val newLetter = contacts.entries
                        .flatMap { entry -> List(entry.value.size + 1) { entry.key } }
                        .getOrNull(firstVisibleItem.index)
                    if (newLetter != null && newLetter != selectedLetter) {
                        selectedLetter = newLetter
                    }
                }
            }
    }

    fun getLetterPosition(letter: String): Int {
        var position = 0
        for ((key, value) in contacts.entries) {
            if (key == letter) return position
            position += value.size + 1
        }
        return 0
    }

    Row(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f)
        ) {
            contacts.map { entry ->
                stickyHeader {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondaryContainer)
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
                    ContactListItem(
                        contact = entry.value[index],
                        onCallButtonClicked = onCallButtonClicked,
                        onMessageButtonClicked = onMessageButtonClicked
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .width(ContactsAppTheme.sizes.medium)
                .padding(end = ContactsAppTheme.paddings.extraExtraSmall)
                .verticalScroll(rememberScrollState())
        ) {
            letters.forEach { letter ->
                Text(
                    text = letter,
                    style = MaterialTheme.typography.labelSmall,
                    color = if (letter == selectedLetter) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    },
                    modifier = Modifier
                        .clickable {
                            val position = getLetterPosition(letter)
                            if (position >= 0) {
                                coroutineScope.launch { listState.scrollToItem(position) }
                            }
                        }
                        .padding(ContactsAppTheme.paddings.extraExtraSmall)
                        .width(ContactsAppTheme.sizes.medium)
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun ContactListItem(
    contact: Contact,
    onCallButtonClicked: (String) -> Unit,
    onMessageButtonClicked: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
            .clickable {
                onCallButtonClicked(contact.phoneNumber)
            }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = contact.photoUri,
                error = painterResource(id = R.drawable.round_person)
            ),
            contentDescription = stringResource(id = R.string.contact_photo),
            contentScale = ContentScale.Crop,
            colorFilter = if (contact.photoUri == null) {
                ColorFilter.tint(MaterialTheme.colorScheme.secondary)
            } else {
                null
            },
            modifier = Modifier
                .padding(ContactsAppTheme.paddings.small)
                .size(60.dp)
                .clip(CircleShape)
        )

        Column(modifier = Modifier.weight(1f, true)) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = ContactsAppTheme.paddings.extraSmall)
            )

            Text(
                text = contact.phoneNumber,
                style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(
            onClick = {
                onCallButtonClicked(contact.phoneNumber)
            },
            modifier = Modifier
                .padding(end = ContactsAppTheme.paddings.small)
                .testTag(CALL_BUTTON + contact.id)
        ) {
            Icon(
                imageVector = Icons.Rounded.Call,
                contentDescription = stringResource(id = R.string.call_your_contact),
                tint = MaterialTheme.colorScheme.primary
            )
        }

        IconButton(
            onClick = {
                onMessageButtonClicked(contact.phoneNumber)
            },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_message),
                contentDescription = stringResource(R.string.message_your_contact),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

const val CALL_BUTTON = "CALL_BUTTON_"