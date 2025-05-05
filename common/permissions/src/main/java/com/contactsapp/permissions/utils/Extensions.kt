package com.contactsapp.permissions.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Context.checkPermission(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        permission
    ) == PackageManager.PERMISSION_GRANTED
}

fun Context.shouldShowRequestPermissionRationale(permission: String): Boolean {
    return ActivityCompat.shouldShowRequestPermissionRationale(
        this as Activity,
        permission
    )
}

fun Context.openSettings() {
    (this as Activity).startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse(PACKAGE + this.packageName)
        )
    )
}

private const val PACKAGE = "package:"
