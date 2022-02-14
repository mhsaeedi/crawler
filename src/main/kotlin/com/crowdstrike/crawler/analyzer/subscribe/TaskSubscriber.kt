package com.crowdstrike.crawler.analyzer.subscribe

import com.crowdstrike.crawler.analyzer.Master
import com.crowdstrike.crawler.shared.repo.Status
import com.crowdstrike.crawler.shared.repo.AnalyzeRepository
import com.crowdstrike.crawler.shared.util.jsonToTask
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

/**
 * @author : Momo
 * @since : 08.10.21, Fri
 *
 **/
@Service
class TaskSubscriber @Autowired constructor(
	private val master: Master,
	private val analyzeRepository: AnalyzeRepository,
) {

	/**
	 * Listens to kafka topic and executes analyze tasks on receiving
	 */
//	@KafkaListener(topics = ["analyze_task_topic"], groupId = "analyze_task_consumers")
//	fun subscribe(task: String) = task.jsonToTask()
//		.apply { status = Status.DELIVERED_TO_ANALYZER }
//		.also { analyzeRepository.saveOrUpdate(it) }
//		.also { master.analyze(it) }

	@KafkaListener(topics = ["analyze_task_topic"], groupId = "analyze_task_consumers")
	fun subscribe(task: String) = logger.error { task.jsonToTask() }

	companion object {
		private val logger = KotlinLogging.logger {  }
	}
}




