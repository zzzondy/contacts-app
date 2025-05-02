package com.contacts_list.presentation.screens.contacts_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.contacts_list.presentation.R
import com.contactsapp.ui.theme.AppTheme
import com.contactsapp.ui.theme.ContactsAppTheme

@Composable
internal fun ContactsListScreenNoPermissionState(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Image(
                imageVector = Icons.Rounded.Lock,
                contentDescription = stringResource(id = R.string.no_read_contacts_permission),
                modifier = Modifier.size(ContactsAppTheme.sizes.extraLarge),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.medium))
        }

        item {
            Text(
                text = stringResource(id = R.string.no_read_contacts_permission),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.small))
        }

        item {
            Text(
                text = stringResource(id = R.string.please_give_read_contacts_permission),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(modifier = Modifier.height(ContactsAppTheme.paddings.large))
        }

        item {
            Button(onClick = {}) {
                Text(text = stringResource(id = R.string.give_permission))
            }
        }
    }
}

@Preview
@Composable
private fun ContactsListScreenNoPermissionStatePreview() {
    AppTheme {
        ContactsListScreenNoPermissionState(modifier = Modifier.fillMaxSize())
    }
}