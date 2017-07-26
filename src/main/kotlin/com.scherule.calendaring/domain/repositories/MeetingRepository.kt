package com.scherule.calendaring.domain.repositories

import com.google.inject.Inject
import com.google.inject.Singleton
import com.scherule.calendaring.domain.Meeting
import org.ektorp.CouchDbConnector
import org.ektorp.support.CouchDbRepositorySupport

@Singleton
class MeetingRepository
@Inject
constructor(connector: CouchDbConnector)
    : CouchDbRepositorySupport<Meeting>(Meeting::class.java, connector) {
}