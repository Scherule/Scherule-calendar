package com.scherule.calendaring.domain.entities

import javax.persistence.*

@Embeddable
class MeetingKeychain(
        val managementKey: String,

        @get:OneToMany(cascade = arrayOf(CascadeType.ALL))
        @get:MapKey(name = "id")
        val participationKeys: Map<ParticipantId, MeetingKey>
) {

    companion object {
        val EMPTY_KEYCHAIN = MeetingKeychain("", emptyMap())
    }

}