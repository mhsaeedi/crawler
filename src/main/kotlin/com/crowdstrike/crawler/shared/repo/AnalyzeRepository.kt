package com.crowdstrike.crawler.shared.repo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*

/**
 * @author : Momo
 * @since : 22.01.22, Sat
 *
 **/
@Repository
class AnalyzeRepository @Autowired constructor(
//	private val redisGraph: RedisGraph,
) {
	companion object {
		private const val analyzer = "analyzer"

		//		private fun Record.toStatus(): Status {
//			TODO("Not yet implemented")
//		}
		private fun Task.toSaveOrUpdateQuery() =
			"SET creationDate: '$creationDate' ,fileUUID: '$fileUUID' , status: $status , ownerUUID: $ownerUUID"
				.run { "MERGE (f:File {taskUUID: '$taskUUID'}) ON MATCH $this ON CREATE $this" }
	}

	fun getStatus(uuid: UUID): Status? = Status.PROCESSING
//		redisGraph.readOnlyQuery(analyzer, "MATCH(f:File) where f.uuid = $uuid, return f.status")
//			.run {
//				when {
//					hasNext() -> next().toStatus()
//					else -> null
//				}
//			}


	fun saveOrUpdate(task: Task) {
//		redisGraph.query(analyzer, task.toSaveOrUpdateQuery())
	}


	fun saveAnalyzeResult(task: Task, pairs: List<Pair<String, Int>>) {
		TODO("Save each pair as a relation to the task")
	}

	fun getShortestPath(startUUID: UUID, targetUUID: UUID): String {
		TODO("Not yet implemented")
	}

	fun getContainedFiles(uuid: UUID, date: LocalDate): Collection<String> {
		TODO("Not yet implemented")
	}

	fun getIncludingFiles(uuid: UUID): Collection<String> {
		TODO("Not yet implemented")
	}

}


