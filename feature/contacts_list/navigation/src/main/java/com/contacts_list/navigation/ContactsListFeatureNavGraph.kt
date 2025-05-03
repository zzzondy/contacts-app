package com.contacts_list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contacts_app.di.daggerViewModel
import com.contacts_list.di.ContactsListFeatureComponentProvider
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreen
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenViewModel

fun NavGraphBuilder.registerContactsListFeatureNavigation(
    navController: NavController,
) {
    this.navigation(
        startDestination = ContactsListFeatureScreens.ContactsListScreen.route,
        route = ContactsListFeatureScreens.FEATURE_NAVIGATION_ROUTE
    ) {
        val context = navController.context.applicationContext
        val contactsListFeatureComponent =
            (context as ContactsListFeatureComponentProvider).provideContactsListFeatureComponent()
        composable(route = ContactsListFeatureScreens.ContactsListScreen.route) {
            val viewModel: ContactsListScreenViewModel = daggerViewModel<ContactsListScreenViewModel> {
                contactsListFeatureComponent.contactsListScreenViewModel
            }

            ContactsListScreen(
                navController = navController,
                contactsListScreenViewModel = viewModel
            )
        }
    }
}