package com.contactsapp.permissions.domain

interface PermissionsRepository {

    suspend fun updatePermissionDialogFlag(flagName: String, isRationaleShow: Boolean)

    suspend fun getPermissionDialogFlag(flagName: String): Boolean
}