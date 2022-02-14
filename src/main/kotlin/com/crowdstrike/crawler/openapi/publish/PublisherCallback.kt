package com.crowdstrike.crawler.openapi.publish

import com.crowdstrike.crawler.shared.repo.AnalyzeRepository
import com.crowdstrike.crawler.shared.repo.Status
import com.crowdstrike.crawler.shared.repo.Task
import mu.KotlinLogging
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFutureCallback

/**
 * @author : Momo
 * @since : 29.01.22, Sat
 *
 **/

class PublisherCallback(
	private val analyzeRepository: AnalyzeRepository,
	private val task: Task,
) : ListenableFutureCallback<SendResult<String, String>> {

	companion object {
		private val logger = KotlinLogging.logger { }
	}

	override fun onSuccess(result: SendResult<String, String>?) =
		analyzeRepository.saveOrUpdate(task.apply { status = Status.PENDING_IN_TASK_QUEUE })

	override fun onFailure(e: Throwable) =
		logger.error { "Error: ${e::class.simpleName} happened. Task:[$task}]" }
			.run { task.status = Status.FAILED_AT_OPEN_API }
			.run { analyzeRepository.saveOrUpdate(task) }
}

