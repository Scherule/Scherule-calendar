package com.scherule.calendaring

import io.restassured.RestAssured
import io.restassured.RestAssured.post
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test


internal class MeetingIntegrationTest {

    companion object {

        @BeforeAll
        @JvmStatic
        fun setup() {
            val port = System.getProperty("server.port")
            if (port == null) {
                RestAssured.port = Integer.valueOf(8080)!!
            } else {
                RestAssured.port = Integer.valueOf(port)!!
            }


            var basePath: String? = System.getProperty("server.base")
            if (basePath == null) {
                basePath = "/rest-garage-sample/"
            }
            RestAssured.basePath = basePath

            var baseHost: String? = System.getProperty("server.host")
            if (baseHost == null) {
                baseHost = "http://localhost"
            }
            RestAssured.baseURI = baseHost

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