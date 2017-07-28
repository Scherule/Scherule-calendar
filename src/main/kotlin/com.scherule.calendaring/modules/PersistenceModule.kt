package com.scherule.calendaring.modules

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Provides
import org.ektorp.CouchDbConnector
import org.ektorp.http.StdHttpClient
import org.ektorp.impl.ObjectMapperFactory
import org.ektorp.impl.StdCouchDbConnector
import org.ektorp.impl.StdCouchDbInstance
import org.ektorp.impl.jackson.EktorpJacksonModule


class PersistenceModule : AbstractModule() {

    @Provides
    fun couchDbConnector(): CouchDbConnector {
        val httpClient = StdHttpClient.Builder()
                .url("http://localhost:5984")
                .username("test")
                .password("test")
                .build()
        val dbInstance = StdCouchDbInstance(httpClient)
        val db = StdCouchDbConnector("calendaring-db", dbInstance, object : ObjectMapperFactory {

            private var instance: ObjectMapper? = null
            private var writeDatesAsTimestamps = false

            @Synchronized
            override fun createObjectMapper(): ObjectMapper {
                var result = instance
                if (result == null) {
                    result = ObjectMapper()
                    applyDefaultConfiguration(result)
                    instance = result
                }
                return result
            }

            override fun createObjectMapper(connector: CouchDbConnector): ObjectMapper {
                val objectMapper = ObjectMapper()
                applyDefaultConfiguration(objectMapper)
                objectMapper
                        .registerModule(EktorpJacksonModule(connector, objectMapper))
                        .registerModule(JodaModule())
                        .registerModule(KotlinModule())
                return objectMapper
            }

            private fun applyDefaultConfiguration(om: ObjectMapper) {
                om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, this.writeDatesAsTimestamps)
            }

        })
        db.createDatabaseIfNotExists()
        return db
    }

    override fun configure() {

    }

}