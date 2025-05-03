package com.contactsapp

import android.app.Application
import android.content.Context
import com.contacts_list.di.ContactsListFeatureComponent
import com.contacts_list.di.ContactsListFeatureComponentProvider
import com.contacts_list.presentation.screens.contacts_list.ContactsListScreenViewModel
import com.contactsapp.di.app.AppComponent
import com.contactsapp.di.app.DaggerAppComponent

class ContactsApp : Application(), ContactsListFeatureComponentProvider {

    private var _appComponent: AppComponent? = null
    val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent didn't initialize"
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create(this)
    }

    override fun provideContactsListFeatureComponent(): ContactsListFeatureComponent =
        appComponent.contactsListFeatureComponentFactory.create()
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is ContactsApp -> appComponent
        else -> applicationContext.appComponent
    }