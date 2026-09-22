package org.example

import io.ktor.client.*
import io.ktor.client.engine.java.*
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.jetbrains.letsPlot.Stat
import org.jetbrains.letsPlot.export.ggsave

import org.jetbrains.letsPlot.geom.geomBar
import org.jetbrains.letsPlot.letsPlot

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

        // Display record ratio by country
        displayCountryRatio(records)
    } catch (e: Exception) {
        println("Error: ${e.message}")
        println("Make sure the Spring Boot service is running on http://localhost:8080")
    } finally {
        client.close()
    }
}

fun displayCountryRatio(records: List<CyberSecurityRecord>) {
    println("\n" + "=".repeat(120))
    println("Record Ratio by Country (Location)")
    println("=".repeat(120))

    // Count records by location
    val locationCounts = records.groupingBy { it.location }.eachCount()
    val totalRecords = records.size

    // Calculate ratios and sort by count descending
    val locationRatios = locationCounts.mapValues { (_, count) ->
        count.toDouble() / totalRecords * 100
    }

    // Display as text table
    println("\n${"Location".padEnd(25)} ${"Count".alignRight(10)} ${"Ratio (%)".alignRight(12)} ${"Bar".padStart(40)}")
    println("-".repeat(95))

    locationCounts.entries.sortedByDescending { it.value }.forEach { (location, count) ->
        val ratio = count.toDouble() / totalRecords * 100
        val barLength = (ratio / 100 * 30).toInt().coerceAtLeast(1)
        val bar = "█".repeat(barLength)
        println("${location.padEnd(25)} ${count.toString().alignRight(10)} ${ratio.toString().padStart(11)}% ${bar}")
    }

    // Create LetsPlot visualization
    println("\nGenerating LetsPlot bar chart...")

    val plotData = mapOf(
        "location" to locationRatios.keys.toList(),
        "ratio" to locationRatios.values.toList()
    )

    // 3. Define the plot structure
    val fig = letsPlot(plotData) + geomBar(
        stat = Stat.identity // Tells Lets-Plot to use the literal 'count' value instead of counting rows
    ) {
        x = "location"
        y = "ratio"
        fill = "location" // Optional: colors bars differently by location
    }

    ggsave(fig, "location_counts.png")
}

// Extension function for right-aligned string formatting
fun String.alignRight(width: Int): String {
    return padStart(width)
}
