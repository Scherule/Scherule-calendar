package com.scherule.calendaring.modules

import com.google.inject.AbstractModule
import org.ektorp.CouchDbConnector
import org.ektorp.http.StdHttpClient
import org.ektorp.impl.StdCouchDbConnector
import org.ektorp.impl.StdCouchDbInstance


class PersistenceModule : AbstractModule() {

    override fun configure() {
        val httpClient = StdHttpClient.Builder()
                .url("http://localhost:5984")
                .build()
        val dbInstance = StdCouchDbInstance(httpClient)
        val db = StdCouchDbConnector("calendaring-db", dbInstance)
        db.createDatabaseIfNotExists()
        bind(CouchDbConnector::class.java).toInstance(db)
    }

}