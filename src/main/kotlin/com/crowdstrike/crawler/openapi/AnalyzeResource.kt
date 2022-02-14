package com.crowdstrike.crawler.openapi


import com.crowdstrike.crawler.shared.repo.AnalyzeRepository
import com.crowdstrike.crawler.shared.repo.Status
import com.crowdstrike.crawler.shared.util.isValid
import com.crowdstrike.crawler.shared.util.toNewTask
import com.crowdstrike.crawler.openapi.publish.TaskPublisher
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate
import java.util.*

/**
 * @author : Momo
 * @since : 08.10.21, Fri
 *
 **/
@RestController
@RequestMapping("/analyze")
class AnalyzeResource @Autowired constructor(
	private val analyzeRepository: AnalyzeRepository,
	private val taskPublisher: TaskPublisher,
) : AnalyzeResourceOpenApi {

	companion object {
		private val logger = KotlinLogging.logger { }
		private fun buildAccepted(uriComponentsBuilder: UriComponentsBuilder, uuid: UUID): ResponseEntity<Void> =
			uriComponentsBuilder.path("/enquiry/{uuid}")
				.buildAndExpand(uuid.toString()).toUri().toString()
				.run {
					ResponseEntity.status(HttpStatus.ACCEPTED)
						.header(HttpHeaders.LOCATION, this)
						.build()
				}
	}

	@PostMapping("/{uuid}", headers = [HttpHeaders.LOCATION],
		consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
	override fun offer(@PathVariable uuid: UUID, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Void> =
		when {
			!uuid.isValid() -> ResponseEntity.badRequest().build()
			else -> uuid.toNewTask()
				.also { analyzeRepository.saveOrUpdate(it) }
				.also { taskPublisher.publish(it) }
				.run { buildAccepted(uriComponentsBuilder, uuid) }
		}


	@GetMapping("/enquiry/{uuid}", headers = [HttpHeaders.LOCATION],
		consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
	override fun enquiry(@PathVariable uuid: UUID, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Status> {
		if (!uuid.isValid()) return ResponseEntity.badRequest().build()
		val status = analyzeRepository.getStatus(uuid)
		return when {
			null == status -> ResponseEntity.notFound().build()
			Status.COMPLETE != status -> ResponseEntity.ok(status)
			else -> uriComponentsBuilder.path("/{uuid}").buildAndExpand(uuid.toString())
				.toUri().toString()
				.run { ResponseEntity.status(HttpStatus.SEE_OTHER).header(HttpHeaders.LOCATION, this).build() }
		}
	}

	@GetMapping("/{uuid}", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
	override fun getAnalyzeResult(@PathVariable uuid: UUID): ResponseEntity<Collection<String>> {
		if (!uuid.isValid()) return ResponseEntity.badRequest().build()
		val result = analyzeRepository.getIncludingFiles(uuid)
		return when {
			result.isEmpty() -> ResponseEntity.noContent().build()
			else -> ResponseEntity.ok(result)
		}
	}


	@GetMapping("/{uuid}/submitted/after/{date}/asc",
		consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
	override fun getContainedFiles(@PathVariable uuid: UUID, @PathVariable date: LocalDate): ResponseEntity<Collection<String>> {
		if (!uuid.isValid() || !date.isValid()) return ResponseEntity.badRequest().build()
		val result = analyzeRepository.getContainedFiles(uuid, date)
		return when {
			result.isEmpty() -> ResponseEntity.noContent().build()
			else -> ResponseEntity.ok(result)
		}
	}


	@GetMapping("/{startUUID}/{targetUUID}", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
	override fun getShortestPath(@PathVariable startUUID: UUID, @PathVariable targetUUID: UUID): ResponseEntity<String> {
		if (!startUUID.isValid() || !targetUUID.isValid()) return ResponseEntity.badRequest().build()
		val result = analyzeRepository.getShortestPath(startUUID, targetUUID)
		return when {
			result.isEmpty() -> ResponseEntity.noContent().build()
			else -> ResponseEntity.ok(result)
		}
	}

	@ExceptionHandler(Exception::class)
	fun handleError(e: Exception): ResponseEntity<Any> =
		logger.run {
			debug { e }
			error { "${e.javaClass.simpleName} happened. This was unexpected." }
		}.run { ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build() }

}




