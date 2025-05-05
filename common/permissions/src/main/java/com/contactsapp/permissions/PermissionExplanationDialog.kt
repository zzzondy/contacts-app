package com.contactsapp.permissions

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource

@Composable
internal fun PermissionExplanationDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    text: String,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onConfirm, modifier = Modifier.testTag(CONFIRM_DIALOG_BUTTON)) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.dismiss))
            }
        },
        title = {
            Text(
                text = title,
                modifier = modifier
            )
        },
        text = {
            Text(text = text)
        }
    )
}

const val FIRST_PERMISSION_DIALOG = "FIRST_PERMISSION_DIALOG"
const val SECOND_PERMISSION_DIALOG = "SECOND_PERMISSION_DIALOG"
const val CONFIRM_DIALOG_BUTTON = "CONFIRM_DIALOG_BUTTON"