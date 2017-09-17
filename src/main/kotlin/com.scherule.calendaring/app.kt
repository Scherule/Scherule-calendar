package com.scherule.calendaring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.jms.annotation.EnableJms

@SpringBootApplication
@EnableEurekaClient
@EnableJms
@ComponentScan(basePackages = arrayOf("com.scherule.calendaring.configuration"))
class CalendaringApplication

fun main(args: Array<String>) {
    SpringApplication.run(CalendaringApplication::class.java, *args)
}
