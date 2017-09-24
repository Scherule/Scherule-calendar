package com.scherule.calendaring.domain

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.math.BigInteger
import java.security.SecureRandom

@Configuration
@EnableJpaRepositories()
class DomainConfig

fun generateRandomHex(byteLength: Int): String {
    val secureRandom = SecureRandom()
    val token = ByteArray(byteLength)
    secureRandom.nextBytes(token)
    return BigInteger(1, token).toString(16)
}