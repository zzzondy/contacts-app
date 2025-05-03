package com.contactsapp.di.permissions

import android.content.Context
import com.contactsapp.permissions.data.PermissionsRepositoryImpl
import com.contactsapp.permissions.data.local.PermissionsLocalDataSource
import com.contactsapp.permissions.data.local.PermissionsLocalDataSourceImpl
import com.contactsapp.permissions.domain.PermissionsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PermissionsModule {

    @Singleton
    @Provides
    fun providePermissionsLocalDataSource(context: Context): PermissionsLocalDataSource =
        PermissionsLocalDataSourceImpl(context)

    @Singleton
    @Provides
    fun providePermissionsRepository(permissionsLocalDataSource: PermissionsLocalDataSource): PermissionsRepository =
        PermissionsRepositoryImpl(permissionsLocalDataSource)
}