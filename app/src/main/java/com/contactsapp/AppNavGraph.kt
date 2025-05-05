package com.contactsapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.contacts_list.navigation.ContactsListFeatureScreens
import com.contacts_list.navigation.registerContactsListFeatureNavigation

@Composable
fun AppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ContactsListFeatureScreens.FEATURE_NAVIGATION_ROUTE,
        modifier = modifier
    ) {
        registerContactsListFeatureNavigation(navController = navController)
    }
}