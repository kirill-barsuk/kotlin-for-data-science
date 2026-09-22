package org.example.config

import org.example.service.CsvDataLoader
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class DataLoadConfig {

    @Bean
    open fun loadDataRunner(csvDataLoader: CsvDataLoader): CommandLineRunner {
        return CommandLineRunner {
            csvDataLoader.loadCsvData()
        }
    }
}
