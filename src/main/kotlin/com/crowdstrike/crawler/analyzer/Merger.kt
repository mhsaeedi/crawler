package com.crowdstrike.crawler.analyzer

import org.springframework.stereotype.Service

/**
 * @author : Momo
 * @since : 23.01.22, Sun
 *
 **/
@Service
class Merger {
	fun merge(results: List<Map<String, Int>>) =
		results.flatMap { it.asSequence() } // join all lists into one
			.groupBy({ it.key }, { it.value }) // Group by filename
			.mapValues { (_, values) -> values.sum() } // Count of filename as value
			.toList()
}
