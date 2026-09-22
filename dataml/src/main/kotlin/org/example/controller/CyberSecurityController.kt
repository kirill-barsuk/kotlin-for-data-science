package org.example.controller

import org.example.entity.CyberSecurityRecord
import org.example.repository.CyberSecurityRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class CyberSecurityController(
    private val repository: CyberSecurityRepository
) {

    @GetMapping("/records")
    fun getAllRecords(): ResponseEntity<List<CyberSecurityRecord>> {
        val records = repository.findAll()
        return ResponseEntity.ok(records)
    }

    @GetMapping("/records/{id}")
    fun getRecordById(@PathVariable id: Long): ResponseEntity<CyberSecurityRecord> {
        return repository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity<CyberSecurityRecord>.notFound().build() }
    }

    @GetMapping("/records/anomalous")
    fun getAnomalousRecords(): ResponseEntity<List<CyberSecurityRecord>> {
        val records = repository.findAll().filter { it.anomalyFlag == 1 }
        return ResponseEntity.ok(records)
    }

    @GetMapping("/records/location/{location}")
    fun getRecordsByLocation(@PathVariable location: String): ResponseEntity<List<CyberSecurityRecord>> {
        val records = repository.findAll().filter { it.location.equals(location, ignoreCase = true) }
        return ResponseEntity.ok(records)
    }
}
