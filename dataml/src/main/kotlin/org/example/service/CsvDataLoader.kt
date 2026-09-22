package org.example.service

import org.example.entity.CyberSecurityRecord
import org.example.repository.CyberSecurityRepository
import org.springframework.stereotype.Service
import java.io.File
import java.time.LocalDateTime

@Service
class CsvDataLoader(
    private val repository: CyberSecurityRepository
) {

    fun loadCsvData() {
        if (repository.count() > 0) {
            println("Database already contains data, skipping CSV load.")
            return
        }

        val csvFile = File("dataml/advanced_cybersecurity_data.csv")
        val records = mutableListOf<CyberSecurityRecord>()

        csvFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val parts = line.split(",")
                if (parts.size >= 8) {
                    try {
                        val record = CyberSecurityRecord(
                            timestamp = LocalDateTime.parse(parts[0].replace(" ", "T")),
                            ipAddress = parts[1],
                            requestType = parts[2],
                            statusCode = parts[3].toInt(),
                            anomalyFlag = parts[4].toInt(),
                            userAgent = parts[5],
                            sessionId = parts[6].toInt(),
                            location = parts[7]
                        )
                        records.add(record)
                    } catch (e: Exception) {
                        println("Error parsing line: $line")
                    }
                }
            }
        }

        repository.saveAll(records)
        println("Loaded ${records.size} records from CSV into database.")
    }
}
