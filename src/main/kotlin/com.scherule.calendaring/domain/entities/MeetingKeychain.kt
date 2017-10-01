package com.scherule.calendaring.domain.entities

import javax.persistence.*

@Embeddable
@Access(AccessType.FIELD)
class MeetingKeychain(
        var managementKey: String = "",

        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        val participationKeys: Map<ParticipantId, MeetingKey> = emptyMap()
) {

    companion object {
        val EMPTY_KEYCHAIN = MeetingKeychain()
    }

}