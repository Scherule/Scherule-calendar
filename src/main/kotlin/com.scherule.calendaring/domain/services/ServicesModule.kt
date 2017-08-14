package com.scherule.calendaring.domain.services

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.scherule.calendaring.domain.KeychainGenerator
import com.scherule.calendaring.domain.repositories.MeetingRepository

class ServicesModule : AbstractModule() {

    override fun configure() {

    }

    @Provides
    fun meetingService(meetingRepository: MeetingRepository,
                       keychainGenerator: KeychainGenerator): MeetingService {
        return MeetingService(meetingRepository, keychainGenerator)
    }

}