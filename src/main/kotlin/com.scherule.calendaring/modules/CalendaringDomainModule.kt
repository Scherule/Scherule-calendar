package com.scherule.calendaring.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule


class CalendaringDomainModule : AbstractModule() {

    override fun configure() {
        bindObjectMapper()
    }

    private fun bindObjectMapper() {
        val objectMapper = ObjectMapper()
        objectMapper
                .registerModule(JodaModule())
                .registerModule(KotlinModule())
        bind(ObjectMapper::class.java).toInstance(objectMapper)
    }

}