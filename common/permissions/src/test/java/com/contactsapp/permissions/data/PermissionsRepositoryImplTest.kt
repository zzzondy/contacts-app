package com.contactsapp.permissions.data

import com.contactsapp.permissions.data.local.PermissionsLocalDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class PermissionsRepositoryImplTest {

    @Mock
    private lateinit var mockPermissionsLocalDataSource: PermissionsLocalDataSource

    private lateinit var repository: PermissionsRepositoryImpl

    @Before
    fun setUp() {
        repository = PermissionsRepositoryImpl(mockPermissionsLocalDataSource)
    }

    @Test
    fun `updatePermissionDialogFlag should delegate to local data source`() = runTest {
        val testFlagName = "TEST_FLAG"
        val testValue = true

        repository.updatePermissionDialogFlag(testFlagName, testValue)

        verify(mockPermissionsLocalDataSource).updatePermissionDialogFlag(testFlagName, testValue)
    }

    @Test
    fun `getPermissionDialogFlag should delegate to local data source and return value`() = runTest {
        val testFlagName = "TEST_FLAG"
        val expectedValue = true
        whenever(mockPermissionsLocalDataSource.getPermissionDialogFlag(testFlagName))
            .thenReturn(expectedValue)

        val result = repository.getPermissionDialogFlag(testFlagName)

        verify(mockPermissionsLocalDataSource).getPermissionDialogFlag(testFlagName)
        assertEquals(expectedValue, result)
    }

    @Test
    fun `getPermissionDialogFlag should return false when local data source returns false`() = runTest {
        val testFlagName = "TEST_FLAG"
        whenever(mockPermissionsLocalDataSource.getPermissionDialogFlag(testFlagName))
            .thenReturn(false)

        val result = repository.getPermissionDialogFlag(testFlagName)

        assertFalse(result)
    }

    @Test
    fun `getPermissionDialogFlag should return true when local data source returns true`() = runTest {
        val testFlagName = "TEST_FLAG"
        whenever(mockPermissionsLocalDataSource.getPermissionDialogFlag(testFlagName))
            .thenReturn(true)

        val result = repository.getPermissionDialogFlag(testFlagName)

        assertTrue(result)
    }
}