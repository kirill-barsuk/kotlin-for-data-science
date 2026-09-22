package org.example

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.*
import io.ktor.client.call.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun main(args: Array<String>) = runBlocking {
    val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val client = HttpClient(Java) {
        install(ContentNegotiation) {
            json(json)
        }
    }

    try {
        println("Fetching cybersecurity records from service...")
        val records = fetchRecords(client)
        println("\nTotal records fetched: ${records.size}\n")
        println("First 10 records:")
        println("-".repeat(120))

        val requestTypes = records.map { it.requestType }.toSet()
        println("Request types: $requestTypes")

        val legalTypes = setOf("GET", "DELETE", "POST", "PUT")
        val anomalyTypes = requestTypes.filter { !legalTypes.contains(it) }.toSet()
        println("Illegal request types: $anomalyTypes")

        records.filter { anomalyTypes.contains(it.requestType) }.forEachIndexed { index, record ->
            println("\n[${index + 1}]")
            println("  ID:            ${record.id}")
            println("  Timestamp:     ${record.timestamp}")
            println("  IP Address:    ${record.ipAddress}")
            println("  Request Type:  ${record.requestType}")
            println("  Status Code:   ${record.statusCode}")
            println("  Anomaly Flag:  ${record.anomalyFlag}")
            println("  User Agent:    ${record.userAgent}")
            println("  Session ID:    ${record.sessionId}")
            println("  Location:      ${record.location}")
        }

        println("\n" + "-".repeat(120))
        println("Display complete.")
    } catch (e: Exception) {
        println("Error: ${e.message}")
        println("Make sure the Spring Boot service is running on http://localhost:8080")
    } finally {
        client.close()
    }
}
