package com.crowdstrike.crawler.analyzer

import com.crowdstrike.crawler.shared.repo.AnalyzeRepository
import com.crowdstrike.crawler.shared.repo.Status
import com.crowdstrike.crawler.shared.repo.Task
import com.crowdstrike.crawler.shared.util.FileUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

/**
 * @author : Momo
 * @since : 21.01.22, Fri
 *
 * Asynchronous batch processing job
 *
 **/
@Service
class Master @Autowired constructor(
	private val worker: Worker,
	private val merger: Merger,
	private val fileUtil: FileUtil,
	private val analyzeRepository: AnalyzeRepository,
) {


	@Async
	fun analyze(task: Task): CompletableFuture<String> =
		fileUtil.files().run {
			when (contains(task.fileUUID.toString())) {
				true -> analyze(task, this)
				false -> fileNotFound(task)
			}
		}


	private fun analyze(task: Task, files: Set<String>): CompletableFuture<String> {
		task.status = Status.PROCESSING
		analyzeRepository.saveOrUpdate(task)
		return fileUtil.chunked(task)
			.map { worker.analyze(it, files) }
			.map { it.get() }
			.run { merger.merge(this) }
			.run { analyzeRepository.saveAnalyzeResult(task, this) }
			.run { CompletableFuture.completedFuture("analyzed!") }
	}


	private fun fileNotFound(task: Task) =
		task.apply { status = Status.FILE_NOT_FOUND }.run { analyzeRepository.saveOrUpdate(this) }
			.run { CompletableFuture.completedFuture("Not Found!") }


}
