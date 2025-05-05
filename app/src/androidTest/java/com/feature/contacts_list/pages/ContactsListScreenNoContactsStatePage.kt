package com.feature.contacts_list.pages

import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import com.contacts_list.presentation.screens.contacts_list.NO_CONTACTS_SCREEN

class ContactsListScreenNoContactsStatePage(composeTestRule: AndroidComposeTestRule<*, *>) {

    val noContactsScreen = composeTestRule.onNodeWithTag(NO_CONTACTS_SCREEN)
}