package com.crowdstrike.crawler

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties
@EnableKafka
@EnableScheduling
class CrawlerApp

fun main(args: Array<String>) {
	runApplication<CrawlerApp>(*args)
}
