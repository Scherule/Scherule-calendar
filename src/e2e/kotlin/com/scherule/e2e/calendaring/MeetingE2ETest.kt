package com.scherule.e2e.calendaring

import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Tag("integration-test")
class MeetingE2ETest {

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
        given().body("""{
                "parameters" : {
                        "between": "1507040100000-1507046400000",
                        "minDuration": 18000000,
                        "minParticipants": 3
                },
                "participants": [
                    {
                        "id":{ "id": "321" },
                        "name": "Greg",
                        "importance": 100,
                        "availability": [
                            {
                                "interval": "1507040100000-1507046400000",
                                "preference":1
                            },
                            {
                                "interval": "1507108500000-1507158000000",
                                "preference": 1
                            }
                        ]
                    }
                ]
            }""")
                .contentType(ContentType.JSON)
                .`when`()
                .post("/meeting")
                .then()
                .statusCode(200)
                .and()
                .body("id.id", notNullValue())
    }

}