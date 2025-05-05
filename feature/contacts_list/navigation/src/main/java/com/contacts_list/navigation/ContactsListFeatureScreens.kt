package com.contacts_list.navigation

sealed class ContactsListFeatureScreens(val route: String) {

    data object ContactsListScreen :
        ContactsListFeatureScreens(route = "contacts_list_feature/contacts_list")

    companion object {
        const val FEATURE_NAVIGATION_ROUTE = "contacts_list_feature_navigation"
    }
}