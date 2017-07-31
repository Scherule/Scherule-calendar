package com.scherule.calendaring.api.verticles

import com.google.inject.AbstractModule
import com.google.inject.Provides

class VerticlesModule : AbstractModule() {

    override fun configure() {

    }

    @Provides
    fun meetingApiVerticle(meetingApi: MeetingApi): MeetingApiVerticle {
        return MeetingApiVerticle(meetingApi)
    }

    @Provides
    fun swaggerApiVerticle(): SwaggerApiVerticle {
        return SwaggerApiVerticle()
    }

}