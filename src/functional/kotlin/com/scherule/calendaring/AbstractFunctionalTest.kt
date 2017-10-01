package com.scherule.calendaring

import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@Category(FunctionalTest::class)
@ContextConfiguration(classes = arrayOf(FunctionalTestContext::class))
@SpringBootTest(classes = arrayOf(CalendaringApplication::class),
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = arrayOf("classpath:application-test.yml"))
class AbstractFunctionalTest