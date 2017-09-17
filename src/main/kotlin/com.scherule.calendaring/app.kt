package com.scherule.calendaring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.jms.annotation.EnableJms

@SpringBootApplication
@EnableEurekaClient
@EnableJms
class CalendaringApplication

fun main(args: Array<String>) {
    SpringApplication.run(CalendaringApplication::class.java, *args)
}
