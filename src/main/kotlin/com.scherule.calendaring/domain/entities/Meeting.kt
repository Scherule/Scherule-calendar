package com.scherule.calendaring.domain.entities

import com.scherule.calendaring.domain.entities.MeetingId.Companion.noMeetingId
import com.scherule.calendaring.domain.entities.MeetingKeychain.Companion.EMPTY_KEYCHAIN
import com.scherule.calendaring.domain.services.KeychainGenerator
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "MEETING")
class Meeting(

        @EmbeddedId
        val meetingId: MeetingId = noMeetingId,

        @Embedded
        @NotNull
        val parameters: MeetingParameters,

        @Column(updatable = false, insertable = false)
        @NotNull
        @JoinColumn(name = "ORGANIZER_ID")
        val organizer: Organizer,

        @OneToMany(mappedBy = "participantId")
        @Size(min = 2)
        val participants: Set<Participant>,

        @Embedded
        val keychain: MeetingKeychain = EMPTY_KEYCHAIN,

        @Column(name = "MEETING_STATE")
        @Enumerated(EnumType.STRING)
        val meetingState: MeetingState = MeetingState.INITIAL

) {

    fun generateKeys(
            keychainGenerator: KeychainGenerator
    ): Meeting {
        return Meeting(
                noMeetingId,
                parameters,
                organizer,
                participants,
                keychainGenerator.generateFor(this),
                MeetingState.CREATED
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Meeting

        if (meetingId != other.meetingId) return false

        return true
    }

    override fun hashCode(): Int {
        return meetingId.hashCode()
    }


}