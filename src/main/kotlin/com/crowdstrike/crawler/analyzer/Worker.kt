package com.crowdstrike.crawler.analyzer

import com.crowdstrike.crawler.shared.util.isValidUUID
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

/**
 * @author : Momo
 * @since : 23.01.22, Sun
 *
 **/
@Service
class Worker {
	companion object {
		private const val space = " "
	}

	@Async
	fun analyze(workload: List<String>, files: Set<String>): CompletableFuture<Map<String, Int>> =
		workload.asSequence()
			.map { it.split(space) } // split each line with space e.g. "a b c" -> [a,b,c]
			.flatten()  // merge all results
			.filter { files.contains(it) }  // keep the ones that have corresponding file
			.filter { it.isValidUUID() } // remove none UUID ones
			.distinct() // remove duplicates
			.groupingBy { it }
			.eachCount()
			.run { CompletableFuture.completedFuture(this) }


}
