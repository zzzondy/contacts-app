package com.contactsapp.di.app

import android.content.Context
import com.contacts_list.di.ContactsListFeatureComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance context: Context): AppComponent
    }

    val contactsListFeatureComponentFactory: ContactsListFeatureComponent.Factory
}