package com.feature.contacts_list

import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import com.contacts_list.domain.repository.ContactsListRepository
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreen
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenViewModel
import com.contactsapp.MainActivity
import com.contactsapp.ui.theme.AppTheme
import com.feature.contacts_list.pages.ContactsListScreenContentStatePage
import com.feature.contacts_list.pages.ContactsListScreenNoContactsStatePage
import com.feature.contacts_list.pages.ContactsListScreenNoPermissionStatePage
import com.kaspersky.components.kautomator.component.text.UiTextView
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class ContactsListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testSuccessUserFlow1WithoutContacts() = runTest {
        val contactsListScreenNoPermissionStatePage =
            ContactsListScreenNoPermissionStatePage(composeTestRule)
        val contactsListScreenNoContactsStatePage = ContactsListScreenNoContactsStatePage(composeTestRule)

        val contactsListRepository = mockk<ContactsListRepository> {
            coEvery { getContacts() } returns emptyMap()
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository
        )

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                AppTheme {
                    ContactsListScreen(
                        contactsListScreenViewModel = viewModel
                    )
                }
            }
        }

        composeTestRule.waitForIdle()

        contactsListScreenNoPermissionStatePage.givePermissionButton.assertExists()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.givePermission()

        contactsListScreenNoContactsStatePage.noContactsScreen.assertExists()
    }


    @Test
    fun testSuccessUserFlow1() = runTest {
        val contactsListScreenNoPermissionStatePage =
            ContactsListScreenNoPermissionStatePage(composeTestRule)
        val contactsListScreenContentStatePage = ContactsListScreenContentStatePage(composeTestRule)

        val contactsListRepository = mockk<ContactsListRepository> {
            coEvery { getContacts() } returns ContactsListScreenContentStatePage.mockContacts
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository
        )

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                AppTheme {
                    ContactsListScreen(
                        contactsListScreenViewModel = viewModel
                    )
                }
            }
        }

        composeTestRule.waitForIdle()

        contactsListScreenNoPermissionStatePage.givePermissionButton.assertExists()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.givePermission()

        contactsListScreenContentStatePage.contactText1.assertExists()
        contactsListScreenContentStatePage.contactText2.assertExists()
    }

    @Test
    fun testSuccessUserFlow1WithOneFailure() = runTest {
        val contactsListScreenNoPermissionStatePage =
            ContactsListScreenNoPermissionStatePage(composeTestRule)
        val contactsListScreenContentStatePage = ContactsListScreenContentStatePage(composeTestRule)

        val contactsListRepository = mockk<ContactsListRepository> {
            coEvery { getContacts() } returns ContactsListScreenContentStatePage.mockContacts
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository,
        )

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                AppTheme {
                    ContactsListScreen(
                        contactsListScreenViewModel = viewModel
                    )
                }
            }
        }

        composeTestRule.waitForIdle()

        contactsListScreenNoPermissionStatePage.givePermissionButton.assertExists()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.rejectPermission()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.firstPermissionDialog.assertExists()
        contactsListScreenNoPermissionStatePage.confirmDialogButton.performClick()

        contactsListScreenNoPermissionStatePage.givePermission()

        contactsListScreenContentStatePage.contactText1.assertExists()
        contactsListScreenContentStatePage.contactText2.assertExists()
    }

    @Test
    fun testSuccessUserFlow1WithTwoFailures() = runTest {
        val contactsListScreenNoPermissionStatePage =
            ContactsListScreenNoPermissionStatePage(composeTestRule)

        val contactsListRepository = mockk<ContactsListRepository> {
            coEvery { getContacts() } returns ContactsListScreenContentStatePage.mockContacts
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository,
        )

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                AppTheme {
                    ContactsListScreen(
                        contactsListScreenViewModel = viewModel
                    )
                }
            }
        }

        composeTestRule.waitForIdle()

        contactsListScreenNoPermissionStatePage.givePermissionButton.assertExists()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.rejectPermission()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.firstPermissionDialog.assertExists()
        contactsListScreenNoPermissionStatePage.confirmDialogButton.performClick()

        contactsListScreenNoPermissionStatePage.rejectPermissionAtAll()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.secondPermissionDialog.assertExists()
        contactsListScreenNoPermissionStatePage.confirmDialogButton.performClick()

        UiTextView {
            withText("О приложении")
        }
    }

    @Test
    fun testSuccessUserFlow2() = runTest {
        val contactsListScreenNoPermissionStatePage =
            ContactsListScreenNoPermissionStatePage(composeTestRule)
        val contactsListScreenContentStatePage = ContactsListScreenContentStatePage(composeTestRule)

        val contactsListRepository = mockk<ContactsListRepository> {
            coEvery { getContacts() } returns ContactsListScreenContentStatePage.mockContacts
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository,
        )

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                AppTheme {
                    ContactsListScreen(
                        contactsListScreenViewModel = viewModel
                    )
                }
            }
        }

        composeTestRule.waitForIdle()
        contactsListScreenNoPermissionStatePage.givePermissionButton.assertExists()
        contactsListScreenNoPermissionStatePage.givePermissionButton.performClick()

        contactsListScreenNoPermissionStatePage.givePermission()

        contactsListScreenContentStatePage.contactText1.assertExists()
        contactsListScreenContentStatePage.contactText2.assertExists()

        contactsListScreenContentStatePage.contacts1CallButton.performClick()

        contactsListScreenContentStatePage.givePermission()

        contactsListScreenContentStatePage.checkCallInProgress()
    }
}
