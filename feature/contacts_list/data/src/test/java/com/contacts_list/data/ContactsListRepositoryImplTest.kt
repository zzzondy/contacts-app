import com.contacts_list.data.ContactsListRepositoryImpl
import com.contacts_list.data.local.ContactsListLocalDataSource
import com.contacts_list.data.local.model.ContactDto
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContactsListRepositoryImplTest {

    private val mockDataSource: ContactsListLocalDataSource = mock()
    private val repository = ContactsListRepositoryImpl(mockDataSource)

    @Test
    fun `empty contacts list returns empty map`() = runTest {
        `when`(mockDataSource.getContacts()).thenReturn(emptyList())
        assertTrue(repository.getContacts().isEmpty())
    }

    @Test
    fun `single contact is grouped correctly`() = runTest {
        val contact = ContactDto("1", "Анна", "+123", "uri")
        `when`(mockDataSource.getContacts()).thenReturn(listOf(contact))

        val result = repository.getContacts()
        assertEquals(1, result.size)
        assertEquals("Анна", result["А"]?.first()?.name)
    }

    @Test
    fun `russian letters sorted alphabetically`() = runTest {
        val contacts = listOf(
            ContactDto("1", "Яна", "+1", null),
            ContactDto("2", "Анна", "+2", null),
            ContactDto("3", "Борис", "+3", null)
        )
        `when`(mockDataSource.getContacts()).thenReturn(contacts)

        val result = repository.getContacts()
        assertEquals(listOf("А", "Б", "Я"), result.keys.toList())
    }

    @Test
    fun `russian ё letter sorted correctly`() = runTest {
        val contacts = listOf(
            ContactDto("1", "ёжик", "+1", null),
            ContactDto("2", "Егор", "+2", null),
            ContactDto("3", "Евгений", "+3", null),
            ContactDto("4", "Ёлка", "+4", null)
        )
        `when`(mockDataSource.getContacts()).thenReturn(contacts)

        val result = repository.getContacts()

        assertEquals(listOf("Е", "Ё"), result.keys.toList())

        assertEquals(listOf("Евгений", "Егор"), result["Е"]?.map { it.name })

        assertEquals(listOf("ёжик", "Ёлка"), result["Ё"]?.map { it.name })
    }

    @Test
    fun `english letters sorted alphabetically`() = runTest {
        val contacts = listOf(
            ContactDto("1", "Bob", "+1", null),
            ContactDto("2", "alice", "+2", null),
            ContactDto("3", "Alice", "+3", null)
        )
        `when`(mockDataSource.getContacts()).thenReturn(contacts)

        val result = repository.getContacts()
        assertEquals(listOf("A", "B"), result.keys.toList())
    }

    @Test
    fun `numeric contacts grouped separately`() = runTest {
        val contacts = listOf(
            ContactDto("1", "123", "+1", null),
            ContactDto("2", "456", "+2", null)
        )
        `when`(mockDataSource.getContacts()).thenReturn(contacts)

        val result = repository.getContacts()
        assertEquals(listOf("1", "4"), result.keys.toList())
    }

    @Test
    fun `special characters grouped separately`() = runTest {
        val contacts = listOf(
            ContactDto("1", "@admin", "+1", null),
            ContactDto("2", "#tag", "+2", null)
        )
        `when`(mockDataSource.getContacts()).thenReturn(contacts)

        val result = repository.getContacts()
        assertEquals(2, result.size)
        assertEquals("@admin", result["@"]?.first()?.name)
        assertEquals("#tag", result["#"]?.first()?.name)
    }

    @Test
    fun `empty name goes to special group`() = runTest {
        `when`(mockDataSource.getContacts()).thenReturn(listOf(
            ContactDto("1", "", "+1", null),
            ContactDto("2", " ", "+2", null)
        ))

        val result = repository.getContacts()
        println(result)
        assertEquals(1, result.size)
        assertEquals(2, result["#"]?.size)
    }

    @Test
    fun `mixed language names sorted correctly`() = runTest {
        val contacts = listOf(
            ContactDto("1", "Anna", "+1", null),
            ContactDto("2", "Анна", "+2", null),
            ContactDto("3", "123", "+3", null)
        )
        `when`(mockDataSource.getContacts()).thenReturn(contacts)

        val result = repository.getContacts()
        assertEquals(listOf("Анна", "Anna", "123"), result.values.flatten().map { it.name })
    }

    @Test
    fun `contact data is preserved during conversion`() = runTest {
        val contact = ContactDto("id1", "Name", "+123", "photoUri")
        `when`(mockDataSource.getContacts()).thenReturn(listOf(contact))

        val result = repository.getContacts()
        val converted = result["N"]?.first()

        assertEquals("Name", converted?.name)
        assertEquals("+123", converted?.phoneNumber)
        assertEquals("photoUri", converted?.photoUri)
    }

    @Test
    fun `null photoUri is preserved`() = runTest {
        val contact = ContactDto("id1", "Name", "+123", null)
        `when`(mockDataSource.getContacts()).thenReturn(listOf(contact))

        val result = repository.getContacts()
        assertNull(result["N"]?.first()?.photoUri)
    }

    @Test
    fun `complex scenario with all cases`() = runTest {
        val contacts = listOf(
            ContactDto("1", "Анна", "+1", null),
            ContactDto("2", "борис", "+2", "uri1"),
            ContactDto("3", "Alice", "+3", null),
            ContactDto("4", "Bob", "+4", null),
            ContactDto("5", "123", "+5", null),
            ContactDto("6", "@test", "+6", null),
            ContactDto("7", "", "+7", null),
            ContactDto("8", "ёжик", "+8", null)
        )
        `when`(mockDataSource.getContacts()).thenReturn(contacts)

        val result = repository.getContacts()

        assertEquals(listOf("А", "Б", "Ё", "A", "B", "#", "1", "@"), result.keys.toList())

        assertEquals(listOf("Анна"), result["А"]?.map { it.name })

        assertEquals(listOf("ёжик"), result["Ё"]?.map { it.name })

        assertEquals(listOf("Alice"), result["A"]?.map { it.name })

        assertEquals(listOf("123"), result["1"]?.map { it.name })
        assertEquals(listOf("@test"), result["@"]?.map { it.name })
        assertEquals(listOf(""), result["#"]?.map { it.name })
    }
}