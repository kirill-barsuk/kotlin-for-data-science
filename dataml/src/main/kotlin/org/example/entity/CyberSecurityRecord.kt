package org.example.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "cybersecurity_records")
data class CyberSecurityRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val timestamp: LocalDateTime,

    @Column(name = "ip_address")
    val ipAddress: String,

    @Column(name = "request_type")
    val requestType: String,

    @Column(name = "status_code")
    val statusCode: Int,

    @Column(name = "anomaly_flag")
    val anomalyFlag: Int,

    @Column(name = "user_agent")
    val userAgent: String,

    @Column(name = "session_id")
    val sessionId: Int,

    val location: String
)
