package com.feature.contacts_list.pages

import android.content.Intent
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.contacts_list.domain.models.Contact
import com.contacts_list.presentation.screens.contacts_list.CALL_BUTTON
import com.contacts_list.presentation.screens.contacts_list.TEL
import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.kaspresso.device.Device
import com.kaspersky.kaspresso.device.phone.Phone

class ContactsListScreenContentStatePage(composeTestRule: AndroidComposeTestRule<*, *>) {

    val contactText1 = composeTestRule.onNodeWithText(mockContacts["И"]!![0].name)
    val contactText2 = composeTestRule.onNodeWithText(mockContacts["К"]!![0].name)

    val contacts1CallButton = composeTestRule.onNodeWithTag(CALL_BUTTON + mockContacts["И"]!![0].id)


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

    fun checkCallInProgress() {
        Intents.init()
        Intents.intended(
            IntentMatchers.hasAction(Intent.ACTION_CALL)
        )
        Intents.release()
    }

    companion object {
        val mockContacts = mapOf(
            "И" to listOf(Contact("1", "Иван Иванов", "+7 800 55 3535", null)),
            "К" to listOf(Contact("2", "Коля", "+1", null))
        )
    }
}