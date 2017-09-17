package com.scherule.calendaring.domain

import com.scherule.calendaring.domain.MeetingId.Companion.noMeetingId
import com.scherule.calendaring.domain.MeetingKeychain.Companion.EMPTY_KEYCHAIN
import com.scherule.calendaring.domain.services.KeychainGenerator
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "MEETING")
class Meeting(

        @get:NotNull
        val meetingId: MeetingId = noMeetingId,

        @get:Embedded
        @get:NotNull
        val parameters: MeetingParameters,

        @get:Column(updatable = false, insertable = false)
        @get:NotNull
        val manager: ParticipantId,

        @get:OneToMany(mappedBy = "id")
        @get:Size(min = 2)
        val participants: Set<Participant>,

        @get:OneToOne
        @get:JoinColumn(name = "KEYCHAIN_ID")
        val keychain: MeetingKeychain = EMPTY_KEYCHAIN,

        @get:Column(name = "MEETING_STATE")
        @get:Enumerated(EnumType.STRING)
        val meetingState: MeetingState = MeetingState.INITIAL

) {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "meeting_seq", sequenceName = "meeting_seq", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_seq")
    val id: Long? = null


    fun generateKeys(
            keychainGenerator: KeychainGenerator
    ): Meeting {
        return Meeting(
                noMeetingId,
                parameters,
                manager,
                participants,
                keychainGenerator.generateFor(this),
                MeetingState.CREATED
        )
    }

}