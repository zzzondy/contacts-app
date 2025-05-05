package com.feature.contacts_list.pages

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import com.contacts_list.presentation.screens.contacts_list.GIVE_PERMISSION_BUTTON
import com.contactsapp.permissions.CONFIRM_DIALOG_BUTTON
import com.contactsapp.permissions.FIRST_PERMISSION_DIALOG
import com.contactsapp.permissions.SECOND_PERMISSION_DIALOG
import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView

class ContactsListScreenNoPermissionStatePage(composeTestRule: AndroidComposeTestRule<*, *>) {

    val givePermissionButton = composeTestRule.onNodeWithTag(GIVE_PERMISSION_BUTTON)

    val firstPermissionDialog = composeTestRule.onNodeWithTag(FIRST_PERMISSION_DIALOG)
    val secondPermissionDialog = composeTestRule.onNodeWithTag(SECOND_PERMISSION_DIALOG)

    val confirmDialogButton = composeTestRule.onNodeWithTag(CONFIRM_DIALOG_BUTTON)

    fun givePermission() {
        try {
            UiTextView {
                containsText("разрешить")
            }
            UiButton {
                withText("Разрешить")
            }.click()
        } catch (e: Exception) {
            println("Permission dialog not shown, probably already granted")
        }
    }

    fun rejectPermission() {
        try {
            UiTextView {
                containsText("разрешить")
            }
            UiButton {
                withText("Отклонить")
            }.click()
        } catch (e: Exception) {
            println("Permission dialog not shown, probably already granted")
        }
    }

    fun rejectPermissionAtAll() {
        try {
            UiButton {
                withText("Запретить и больше не спрашивать")
            }.click()
        } catch (e: Exception) {
            println("Permission dialog not shown, probably already granted")
        }
    }
}