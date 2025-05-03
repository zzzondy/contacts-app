package com.contacts_list.data.utils

import com.contacts_list.data.local.model.ContactDto
import com.contacts_list.domain.models.Contact
import org.junit.Assert.assertEquals
import org.junit.Test


class MapperTest {

    @Test
    fun `should return domain model`() {
        val contactDto = ContactDto("1", "name", "phone", null)

        assertEquals(Contact("1", "name", "phone", null), contactDto.toDomain())
    }
}