package com.scherule.calendaring.domain

import java.math.BigInteger
import java.security.SecureRandom

fun generateRandomHex(byteLength: Int): String {
    val secureRandom = SecureRandom()
    val token = ByteArray(byteLength)
    secureRandom.nextBytes(token)
    return BigInteger(1, token).toString(16)
}