package com.scherule.calendaring.endpoints.messaging.scheduling

import com.scherule.calendaring.domain.entities.MeetingParameters
import com.scherule.calendaring.domain.entities.Participant
import com.scherule.calendaring.domain.entities.SchedulingAlgorithm
import com.scherule.calendaring.domain.entities.SchedulingJobId

class SchedulingJob(
        val schedulingJobId: SchedulingJobId,
        val algorithm: SchedulingAlgorithm,
        val parameters: MeetingParameters,
        val participants: Set<Participant>
)