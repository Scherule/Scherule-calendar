package com.scherule.calendaring.api

open class MainApiException(val statusCode: Int, val statusMessage: String) : Exception() {

    companion object {
        val INTERNAL_SERVER_ERROR = MainApiException(500, "Internal Server Error")
    }

}