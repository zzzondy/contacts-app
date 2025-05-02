package com.contactsapp.permissions.data.local

interface PermissionsLocalDataSource {

    suspend fun updatePermissionDialogFlag(flagName: String, isRationaleShow: Boolean)

    suspend fun getPermissionDialogFlag(flagName: String): Boolean
}