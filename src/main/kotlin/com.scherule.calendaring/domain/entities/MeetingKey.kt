package com.scherule.calendaring.domain.entities

import javax.persistence.*

@Entity
@Table(name = "MEETING_KEYS")
@Embeddable
data class MeetingKey(
        @get:EmbeddedId
        val owner: ParticipantId,
        val hash: String
) {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "meeting_key_id", sequenceName = "meeting_key_id", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meeting_key_id")
    val id: Long? = null
    
}