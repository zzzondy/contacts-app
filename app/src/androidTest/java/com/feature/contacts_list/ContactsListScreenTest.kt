package com.feature.contacts_list

import android.Manifest
import android.os.Build
import androidx.activity.compose.setContent
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.performClick
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.contacts_list.domain.repository.ContactsListRepository
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreen
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenNoContactsState
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenViewModel
import com.contactsapp.MainActivity
import com.contactsapp.permissions.domain.PermissionsRepository
import com.contactsapp.ui.theme.AppTheme
import com.feature.contacts_list.pages.ContactsListScreenContentStatePage
import com.feature.contacts_list.pages.ContactsListScreenNoPermissionStatePage
import com.kaspersky.components.kautomator.component.text.UiTextView
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ContactsListScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @After
    fun tearDown() {
        composeTestRule.activityRule.scenario.close()
    }

    @Test
    fun testSuccessUserFlow1() = runTest {
        val contactsListScreenNoPermissionStatePage =
            ContactsListScreenNoPermissionStatePage(composeTestRule)
        val contactsListScreenContentStatePage = ContactsListScreenContentStatePage(composeTestRule)

        val contactsListRepository = mockk<ContactsListRepository> {
            coEvery { getContacts() } returns ContactsListScreenContentStatePage.mockContacts
        }
        val permissionsRepository = mockk<PermissionsRepository> {
            coEvery { getPermissionDialogFlag(Manifest.permission.READ_CONTACTS) } returns false
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository,
            permissionsRepository
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
        val permissionsRepository = mockk<PermissionsRepository> {
            coEvery { getPermissionDialogFlag(Manifest.permission.READ_CONTACTS) } returns false
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository,
            permissionsRepository
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
    fun testSuccessUserFlow2() = runTest {
        val contactsListScreenNoPermissionStatePage =
            ContactsListScreenNoPermissionStatePage(composeTestRule)
        val contactsListScreenContentStatePage = ContactsListScreenContentStatePage(composeTestRule)

        val contactsListRepository = mockk<ContactsListRepository> {
            coEvery { getContacts() } returns ContactsListScreenContentStatePage.mockContacts
        }
        val permissionsRepository = mockk<PermissionsRepository> {
            coEvery { getPermissionDialogFlag(Manifest.permission.READ_CONTACTS) } returns false
        }

        val viewModel = ContactsListScreenViewModel(
            contactsListRepository,
            permissionsRepository
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
    }
}
