package com.crowdstrike.crawler.analyzer

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor


/**
 * @author : Momo
 * @since : 22.01.22, Sat
 *
 **/
@Configuration
class ThreadPoolConfig {

	companion object {
		private val threadPoolSize = 4 * Runtime.getRuntime().availableProcessors()
		private const val analyzer = "analyzer-"
	}

	@Bean
	@Primary
	fun analyzeExecutor() =
		ThreadPoolTaskExecutor().apply {
			corePoolSize = threadPoolSize
			maxPoolSize = threadPoolSize
			setQueueCapacity(Int.MAX_VALUE)
			setThreadNamePrefix(analyzer)
			initialize()
		}

}
