package org.example

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

suspend fun fetchRecords(client: HttpClient): List<CyberSecurityRecord> {
    return client.get("http://localhost:8080/api/records")
        .body<List<CyberSecurityRecord>>()
}