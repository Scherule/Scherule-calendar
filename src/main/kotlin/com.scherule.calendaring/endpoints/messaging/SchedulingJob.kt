package com.scherule.calendaring.endpoints.messaging

import com.scherule.calendaring.domain.MeetingParameters
import com.scherule.calendaring.domain.Participant
import com.scherule.calendaring.domain.SchedulingAlgorithm
import com.scherule.calendaring.domain.SchedulingJobId

class SchedulingJob(
        val id: SchedulingJobId,
        val algorithm: SchedulingAlgorithm,
        val parameters: MeetingParameters,
        val participants: Set<Participant>
)