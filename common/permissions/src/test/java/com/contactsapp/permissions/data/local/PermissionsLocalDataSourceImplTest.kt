package com.contactsapp.permissions.data.local

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class PermissionsLocalDataSourceImplTest {

    private lateinit var context: Context
    private lateinit var dataSource: PermissionsLocalDataSourceImpl

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        dataSource = PermissionsLocalDataSourceImpl(context)

        context.getSharedPreferences(
            PermissionsLocalDataSourceImpl.PERMISSIONS_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit().clear().apply()
    }

    @After
    fun tearDown() {
        context.getSharedPreferences(
            PermissionsLocalDataSourceImpl.PERMISSIONS_SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        ).edit().clear().apply()
    }

    @Test
    fun `updatePermissionDialogFlag should save boolean value`() = runTest {
        val testFlagName = "TEST_FLAG"
        val testValue = true

        dataSource.updatePermissionDialogFlag(testFlagName, testValue)
        val result = dataSource.getPermissionDialogFlag(testFlagName)

        assertEquals(testValue, result)
    }

    @Test
    fun `getPermissionDialogFlag should return false when flag not exists`() = runTest {
        val nonExistentFlag = "NON_EXISTENT_FLAG"

        val result = dataSource.getPermissionDialogFlag(nonExistentFlag)

        assertFalse(result)
    }

    @Test
    fun `update and get should work with multiple flags`() = runTest {
        val flag1 = "FLAG_1"
        val value1 = true
        val flag2 = "FLAG_2"
        val value2 = false

        dataSource.updatePermissionDialogFlag(flag1, value1)
        dataSource.updatePermissionDialogFlag(flag2, value2)
        val result1 = dataSource.getPermissionDialogFlag(flag1)
        val result2 = dataSource.getPermissionDialogFlag(flag2)

        assertEquals(value1, result1)
        assertEquals(value2, result2)
    }

    @Test
    fun `update should override previous value`() = runTest {
        val flag = "TEST_FLAG"
        val initialValue = false
        val updatedValue = true

        dataSource.updatePermissionDialogFlag(flag, initialValue)
        val firstResult = dataSource.getPermissionDialogFlag(flag)

        dataSource.updatePermissionDialogFlag(flag, updatedValue)
        val secondResult = dataSource.getPermissionDialogFlag(flag)

        assertEquals(initialValue, firstResult)
        assertEquals(updatedValue, secondResult)
    }
}