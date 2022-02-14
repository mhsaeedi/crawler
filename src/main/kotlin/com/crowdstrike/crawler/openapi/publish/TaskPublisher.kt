package com.crowdstrike.crawler.openapi.publish

import com.crowdstrike.crawler.shared.repo.AnalyzeRepository
import com.crowdstrike.crawler.shared.repo.Task
import com.crowdstrike.crawler.shared.util.toJson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


/**
 * @author : Momo
 * @since : 28.01.22, Fri
 */
@Service
class TaskPublisher @Autowired constructor(
	private val kafkaTemplate: KafkaTemplate<String, String>,
	private val analyzeRepository: AnalyzeRepository,
) {

	fun publish(task: Task) =
		kafkaTemplate.send("analyze_task_topic", task.toJson())
			.run { addCallback(PublisherCallback(analyzeRepository, task)) }

}

