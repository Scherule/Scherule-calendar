package com.scherule.calendaring.endpoints.messaging

import com.scherule.calendaring.domain.repositories.MeetingRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SchedulingResultsConsumer
@Autowired constructor(
        private val meetingRepository: MeetingRepository
) {

    companion object {
        val log = LoggerFactory.getLogger(SchedulingResultsConsumer::class.java)!!
    }

    @SuppressWarnings("unused")
    fun handleSchedulingResults(schedulingResults: Map<String, String>) {
        log.debug("Received scheduling results")
    }

}