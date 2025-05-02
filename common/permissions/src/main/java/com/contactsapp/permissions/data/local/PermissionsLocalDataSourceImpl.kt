package com.contactsapp.permissions.data.local

import android.content.Context

class PermissionsLocalDataSourceImpl(private val context: Context) : PermissionsLocalDataSource {
    override suspend fun updatePermissionDialogFlag(
        flagName: String,
        isRationaleShow: Boolean
    ) {
        context.getSharedPreferences(PERMISSIONS_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .apply {
                putBoolean(flagName, isRationaleShow)
                apply()
            }
    }

    override suspend fun getPermissionDialogFlag(flagName: String): Boolean {
        return context.getSharedPreferences(PERMISSIONS_SHARED_PREFERENCES, Context.MODE_PRIVATE)
            .getBoolean(flagName, false)
    }

    companion object {
        private const val PERMISSIONS_SHARED_PREFERENCES = "PERMISSIONS_SHARED_PREFERENCES"
    }
}