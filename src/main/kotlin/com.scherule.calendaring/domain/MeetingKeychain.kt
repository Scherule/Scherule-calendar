package com.scherule.calendaring.domain

class MeetingKeychain(
        val managementKey: MeetingKey,
        val participationKeys: Map<ParticipantId, MeetingKey>
) {

    companion object {
        val EMPTY_KEYCHAIN = MeetingKeychain(MeetingKey(ParticipantId.participantId("abc"), ""), emptyMap())
    }

}