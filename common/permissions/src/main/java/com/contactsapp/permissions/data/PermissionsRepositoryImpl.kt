package com.contactsapp.permissions.data

import com.contactsapp.permissions.data.local.PermissionsLocalDataSource
import com.contactsapp.permissions.domain.PermissionsRepository

class PermissionsRepositoryImpl(
    private val permissionsLocalDataSource: PermissionsLocalDataSource
) : PermissionsRepository {

    override suspend fun updatePermissionDialogFlag(flagName: String, isRationaleShow: Boolean) {
        permissionsLocalDataSource.updatePermissionDialogFlag(flagName, isRationaleShow)
    }

    override suspend fun getPermissionDialogFlag(flagName: String): Boolean {
        return permissionsLocalDataSource.getPermissionDialogFlag(flagName)
    }
}