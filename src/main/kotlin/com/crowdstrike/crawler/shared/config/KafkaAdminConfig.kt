package com.crowdstrike.crawler.shared.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

/**
 * @author : Mohammad <mohammad.saeedi></mohammad.saeedi>@visual-meta.com>
 * @since : 08.02.22, Tue
 */
@Configuration
class KafkaAdminConfig {
	@Bean
	fun newTopic() =
		TopicBuilder
			.name("analyze_task_topic")
			.partitions(3)
			.replicas(3)
			.build()
}
