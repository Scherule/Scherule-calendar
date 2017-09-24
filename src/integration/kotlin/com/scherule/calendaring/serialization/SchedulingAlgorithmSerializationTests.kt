package com.scherule.calendaring.serialization

import com.scherule.calendaring.domain.entities.SchedulingAlgorithm
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@JsonTest
class SchedulingAlgorithmSerializationTests {

    @Autowired
    lateinit var schedulingAlgorithmTester: JacksonTester<SchedulingAlgorithm>

    @Test
    fun canSerializeSchedulingAlgorithm() {
        assertThat(schedulingAlgorithmTester.write(SchedulingAlgorithm(
                type = "intervalProjection"
        ))).isEqualTo("{\"type\":\"intervalProjection\"}")
    }

}