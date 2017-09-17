package com.scherule.calendaring.serialization

import org.assertj.core.api.Assertions.assertThat
import com.scherule.calendaring.domain.Availability
import org.joda.time.Interval
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@JsonTest
class AvailabilitySerializationTests {

    @Autowired
    lateinit var availabilityTester: JacksonTester<Availability>

    @Test
    fun canSerializeAvailability() {
        assertThat(availabilityTester.write(Availability.availableIn(
                Interval.parse("2017-10-03T14:15Z/2017-10-03T16:00Z"), 90)
        )).isEqualTo("{\"interval\":\"1507040100000-1507046400000\",\"preference\":90}")
    }

}