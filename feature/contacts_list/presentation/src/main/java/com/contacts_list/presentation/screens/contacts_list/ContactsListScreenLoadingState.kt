package com.contacts_list.presentation.screens.contacts_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.contactsapp.ui.modifiers.shimmerEffect
import com.contactsapp.ui.theme.AppTheme
import com.contactsapp.ui.theme.ContactsAppTheme


@Composable
internal fun ContactsListScreenLoadingState(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        userScrollEnabled = false
    ) {
        LOADING_LIST.map {
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
                        text = "",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .fillMaxWidth(0.05f)
                            .shimmerEffect(true, MaterialTheme.shapes.medium)
                    )
                }
            }
            items(LOADING_ITEMS_COUNT) {
                ContactListItem()
            }
        }
    }
}

@Composable
private fun ContactListItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .padding(ContactsAppTheme.paddings.small)
                .size(60.dp)
                .clip(CircleShape)
                .shimmerEffect(true, CircleShape)
        )

        Column(modifier = Modifier.weight(1f, true)) {
            Text(
                text = "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(bottom = ContactsAppTheme.paddings.extraSmall)
                    .fillMaxWidth(0.7f)
                    .shimmerEffect(true, MaterialTheme.shapes.medium)
            )

            Text(
                text = "",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .shimmerEffect(true, MaterialTheme.shapes.medium)
            )
        }

        IconButton(
            onClick = {
            },
            enabled = false,
            modifier = Modifier
                .padding(end = ContactsAppTheme.paddings.small)
                .shimmerEffect(true, CircleShape)
        ) {
        }

        IconButton(
            onClick = {
            },
            enabled = false,
            modifier = Modifier.shimmerEffect(true, CircleShape)
        ) {
        }
    }
}

@Preview
@Composable
private fun ContactsListScreenLoadingStatePreview() {
    AppTheme {
        ContactsListScreenLoadingState(Modifier.fillMaxSize())
    }
}

private val LOADING_LIST = listOf(1, 2, 3)
private const val LOADING_ITEMS_COUNT = 3