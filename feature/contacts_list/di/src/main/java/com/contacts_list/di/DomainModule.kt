package com.contacts_list.di

import com.contacts_list.data.ContactsListRepositoryImpl
import com.contacts_list.data.local.ContactsListLocalDataSource
import com.contacts_list.domain.repository.ContactsListRepository
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @ContactsListFeatureScope
    @Provides
    fun provideContactsListRepository(contactsListLocalDataSource: ContactsListLocalDataSource): ContactsListRepository =
        ContactsListRepositoryImpl(contactsListLocalDataSource)
}