package com.contacts_list.di

import android.content.Context
import com.contacts_list.data.local.ContactsListLocalDataSource
import com.contacts_list.data.local.ContactsListLocalDataSourceImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {

    @ContactsListFeatureScope
    @Provides
    fun provideContactsListLocalDataSource(context: Context): ContactsListLocalDataSource =
        ContactsListLocalDataSourceImpl(context)
}