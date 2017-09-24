package com.scherule.calendaring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class CalendaringApplication

fun main(args: Array<String>) {
    SpringApplication.run(CalendaringApplication::class.java, *args)
}
