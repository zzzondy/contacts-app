package com.contacts_list.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreen
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenViewModel

@Composable
fun NavGraphBuilder.registerContactsListFeatureNavigation(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    this.navigation(
        startDestination = ContactsListFeatureScreens.ContactsListScreen.route,
        route = ContactsListFeatureScreens.FEATURE_NAVIGATION_ROUTE
    ) {
        composable(route = ContactsListFeatureScreens.ContactsListScreen.route) {
            ContactsListScreen(
                navController = navController,
                contactsListScreenViewModel = ContactsListScreenViewModel()
            )
        }
    }
}