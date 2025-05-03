package com.contactsapp.di.app

import com.contactsapp.di.permissions.PermissionsModule
import dagger.Module

@Module(
    includes = [PermissionsModule::class]
)
class AppModule