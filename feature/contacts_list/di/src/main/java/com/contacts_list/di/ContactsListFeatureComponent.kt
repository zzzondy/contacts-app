package com.contacts_list.di

import com.contacts_list.di.contacts_list.ContactsListScreenModule
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenViewModel
import dagger.Subcomponent

@ContactsListFeatureScope
@Subcomponent(
    modules = [DataModule::class, DomainModule::class, ContactsListScreenModule::class]
)
interface ContactsListFeatureComponent {

    @Subcomponent.Factory
    interface Factory {

        fun create(): ContactsListFeatureComponent
    }

    val contactsListScreenViewModel: ContactsListScreenViewModel
}