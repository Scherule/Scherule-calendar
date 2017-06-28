package com.scherule.calendaring.domain

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import javax.inject.Inject

@ExtendWith(InjectorExtension::class)
internal class ParticipantIdTest {

    @Inject
    lateinit var objectMapper: ObjectMapper

    @Test
    fun canSerialize() {
        assertEquals(
                "{\"id\":\"123\"}",
                objectMapper.writeValueAsString(ParticipantId.participantId("123"))
        )
    }

}