package com.crowdstrike.crawler.shared.repo

import java.time.LocalDateTime
import java.util.*

/**
 * @author : Momo
 * @since : 22.01.22, Sat
 *
 **/
enum class Status {
	NEW,
	PENDING_IN_TASK_QUEUE,
	FAILED_AT_OPEN_API,
	DELIVERED_TO_ANALYZER,
	FILE_NOT_FOUND,
	PROCESSING,
	COMPLETE,
}

data class Task(
	val taskUUID: UUID, // UUID of the task
	val creationDate: LocalDateTime, // - timestamp when the request was submitted
	val fileUUID: UUID, // - a UUID in standard notation, represents processing file name
	var status: Status, // - the status of the analysis corresponding to this task
	var ownerUUID: UUID? = null, // UUID of container that is processing this task
)
