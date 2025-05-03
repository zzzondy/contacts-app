package com.contacts_list.di.contacts_list

import com.contacts_list.di.ContactsListFeatureScope
import com.contacts_list.domain.repository.ContactsListRepository
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenViewModel
import dagger.Module
import dagger.Provides

@Module()
class ContactsListScreenModule {

    @ContactsListFeatureScope
    @Provides
    fun provideContactsListScreenViewModel(contactsListRepository: ContactsListRepository): ContactsListScreenViewModel =
        ContactsListScreenViewModel(contactsListRepository)
}