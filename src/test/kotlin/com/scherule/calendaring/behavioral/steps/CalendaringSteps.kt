package com.scherule.calendaring.behavioral.steps

import com.scherule.calendaring.domain.Meeting
import com.scherule.calendaring.domain.ParticipantId
import com.scherule.calendaring.domain.services.MeetingService
import com.scherule.calendaring.support.builders.MeetingBuilder
import com.scherule.calendaring.support.builders.MeetingBuilder.Companion.aMeeting
import com.scherule.calendaring.support.builders.ParticipantBuilder
import com.scherule.calendaring.support.builders.ParticipantBuilder.Companion.aParticipant
import com.scherule.scheduling.converters.IntervalConverter
import cucumber.api.Transform
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import cucumber.runtime.java.guice.ScenarioScoped
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.Interval
import java.util.*
import javax.inject.Inject

@ScenarioScoped
internal class CalendaringSteps @Inject constructor(
        private val meetingService: MeetingService
) {

    var meetingBuilder: MeetingBuilder = aMeeting()

    var participantBuilderMap: TreeMap<String, ParticipantBuilder> = TreeMap()

    var meeting: Meeting? = null;

    @Given("there is a organizer '([^']*)' who wants to schedule a meeting")
    fun givenThereIsUserWhoWantsToScheduleMeeting(
            participantName: String
    ) {
        meetingBuilder = aMeeting().withManager(ParticipantId.participantId(participantName))
    }

    @Given("this meeting has to happen in period '(.*)'")
    fun givenThisMeetingHasToHappenInPeriod(
            @Transform(IntervalConverter::class) interval: Interval
    ) {
        meetingBuilder.inBetween(interval)
    }

    @Given("this meeting has minimum participants count of '(.*)'")
    fun givenThisMeetingHasMinimumParticipantsCountOf(
            minParticipants: Int
    ) {
        meetingBuilder.withMinParticipants(minParticipants)
    }

    @Given("this meeting has a participant '([^']*)' with importance '(.*)'\$")
    fun givenThisMeetingHasParticipantWithImportance(
            participantName: String,
            importance: Int
    ) {
        participantBuilderMap.put(
                participantName,
                aParticipant(participantName)
                        .withImportance(importance)
        )
    }

    @Given("this participant declares availability '(.*)'\$")
    fun givenThisParticipantDeclaresAvailability(
            @Transform(IntervalConverter::class) interval: Interval
    ) {
        participantBuilderMap.lastEntry().value.withAvailability(interval)
    }

    @When("the organizer creates this meeting")
    fun whenUserCreatesThisMeeting() {
        meeting = meetingService.createMeeting(
                meetingBuilder
                        .withParticipants(
                                *participantBuilderMap.values.map(ParticipantBuilder::build).toTypedArray()
                        )
                        .build()
        ).toBlocking().value()
    }

    @Then("this meeting is created")
    fun thenThisMeetingIsCreated() {
        assertThat(meetingService.getMeeting(meeting!!.meetingId)).isEqualTo(meeting)
    }

    @Then("management key bound to '([^']*)' can be obtained for this meeting")
    fun thenTheOrganizerReceivesUniqueMeetingManagementLinks(
            participantName: String
    ) {
        assertThat(meeting!!.keychain.managementKey.owner)
                .isEqualTo(ParticipantId(participantName))
    }

    @Then("participation key bound to participant '([^']*)' can be obtained for this meeting")
    fun thenParticipantReceivesInvitationLinkForThatMeeting(
            participantName: String
    ) {
        assertThat(meeting!!.keychain.participationKeys).containsKey(ParticipantId(participantName))
    }

}