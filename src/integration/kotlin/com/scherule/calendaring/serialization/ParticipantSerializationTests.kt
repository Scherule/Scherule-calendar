package com.scherule.calendaring.serialization

import com.scherule.calendaring.domain.*
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.Interval
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@JsonTest
class ParticipantSerializationTests {

    @Autowired
    lateinit var participantIdTester: JacksonTester<ParticipantId>

    @Autowired
    lateinit var participantTester: JacksonTester<Participant>

    @Test
    fun canSerializeParticipantId() {
        assertThat(participantIdTester.write(ParticipantId.participantId("123")))
                .isEqualTo("{\"id\":\"123\"}")
    }

    @Test
    fun canSerializeParticipant() {
        assertThat(participantTester.write(Participant(
                participantId = ParticipantId.participantId("321"),
                name = "Greg",
                importance = 100,
                availability = setOf(
                        Availability.availableIn(Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z")),
                        Availability.availableIn(Interval.parse("2017-10-04T09:15Z/2017-10-04T23:00Z"))
                )
        ))).isEqualTo("{\"id\":{\"id\":\"321\"},\"name\":\"Greg\",\"importance\":100,\"availability\":[{\"interval\":\"1507040100000-1507046400000\",\"preference\":1},{\"interval\":\"1507108500000-1507158000000\",\"preference\":1}]}")
    }

}