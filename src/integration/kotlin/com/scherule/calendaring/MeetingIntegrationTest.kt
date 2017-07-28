package com.scherule.calendaring

import io.restassured.RestAssured
import io.restassured.RestAssured.post
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("integration-test")
class MeetingIntegrationTest {

    companion object {

        @BeforeAll
        @JvmStatic
        fun setup() {
            RestAssured.baseURI = System.getProperty("http.host", "http://localhost")
            RestAssured.port = Integer.valueOf(System.getProperty("http.port", "8080"))
        }

    }

    @Test
    fun canCreateMeeting() {
        post("/meeting").then()
                .statusCode(201)
                .and()
                .body("id.id", notNullValue())
    }

}