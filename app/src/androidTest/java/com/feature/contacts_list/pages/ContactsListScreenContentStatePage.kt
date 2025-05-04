package com.feature.contacts_list.pages

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.contacts_list.domain.models.Contact
import com.contacts_list.presentation.screens.contacts_list.CALL_BUTTON

class ContactsListScreenContentStatePage(composeTestRule: AndroidComposeTestRule<*, *>) {

    val contactText1 = composeTestRule.onNodeWithText(mockContacts["И"]!![0].name)
    val contactText2 = composeTestRule.onNodeWithText(mockContacts["К"]!![0].name)

    val contacts1CallButton = composeTestRule.onNodeWithTag(CALL_BUTTON + mockContacts["И"]!![0].id)


    companion object {
        val mockContacts = mapOf(
            "И" to listOf(Contact("1", "Иван Иванов", "+7 800 55 3535", null)),
            "К" to listOf(Contact("2", "Коля", "+1", null))
        )
    }
}