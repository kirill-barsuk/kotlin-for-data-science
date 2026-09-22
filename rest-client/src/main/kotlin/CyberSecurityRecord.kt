package org.example

import kotlinx.serialization.Serializable

@Serializable
data class CyberSecurityRecord(
    val id: Long,
    val timestamp: String,
    val ipAddress: String,
    val requestType: String,
    val statusCode: Int,
    val anomalyFlag: Int,
    val userAgent: String,
    val sessionId: Int,
    val location: String
)
