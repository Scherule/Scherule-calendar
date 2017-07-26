package com.scherule.calendaring.modules

import com.google.inject.AbstractModule
import com.google.inject.multibindings.Multibinder
import com.scherule.calendaring.endpoints.Endpoint
import com.scherule.calendaring.endpoints.MeetingHttpEndpoint

class EndpointModule : AbstractModule() {

    override fun configure() {
        Multibinder.newSetBinder(binder(), Endpoint::class.java)
                .addBinding().to(MeetingHttpEndpoint::class.java)
    }

}