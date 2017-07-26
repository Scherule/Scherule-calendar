package com.scherule.calendaring.endpoints

import io.vertx.rxjava.ext.web.Router

interface Endpoint {

    fun mount(router: Router)

}