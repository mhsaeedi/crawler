package com.crowdstrike.crawler.shared.util

import com.crowdstrike.crawler.shared.prop.CrawlerProperties
import com.crowdstrike.crawler.shared.repo.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.isRegularFile

/**
 * @author : Momo
 * @since : 23.01.22, Sun
 *
 **/
@Service
class FileUtil @Autowired constructor(
	private val crawlerProperties: CrawlerProperties,
) {

	fun files() =
		File(crawlerProperties.pathToDirectory).list()
			?.map { Paths.get(it) }
			?.filter { it.isRegularFile() }
			?.map { it.fileName.toString() }
			?.filter { it.isValidUUID() }
			?.toSet() ?: setOf()

	fun chunked(task: Task) =
		with(crawlerProperties) {
			Files.readAllLines(Paths.get(pathToDirectory, task.fileUUID.toString()))
				.chunked(chunkSize!!)
		}
}
