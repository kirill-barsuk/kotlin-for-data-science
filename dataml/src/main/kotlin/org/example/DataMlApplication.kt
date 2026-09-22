package org.example

import org.example.service.CsvDataLoader
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class DataMlApplication

fun main(args: Array<String>) {
    runApplication<DataMlApplication>(*args)
}
