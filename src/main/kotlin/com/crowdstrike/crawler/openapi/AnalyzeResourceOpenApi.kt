package com.crowdstrike.crawler.openapi

import com.crowdstrike.crawler.shared.repo.Status
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.util.UriComponentsBuilder
import java.time.LocalDate
import java.util.*

/**
 * @author : Momo
 * @since : 18.10.21, Mon
 *
 **/
@Tag(name = "Analyze", description = "Analyze api")
interface AnalyzeResourceOpenApi {
	@Operation(
		summary = "Add a analyze task",
		description = "Request a file to be analysed by specifying its ID",
	)
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "202",
				description = "When task is accepted, returns location for enquiry status of analyze process",
				headers = [
					Header(
						name = HttpHeaders.LOCATION,
						description = "Location of resource enquiry",
						schema = Schema(implementation = String::class, example = "/enquiry/6e946e28-7c8d-11ec-90d6-0242ac120003"),
						required = true
					)
				],
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Malformed UUID sent by client",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "500",
				description = "Unexpected error",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
		],
	)
	fun offer(uuid: UUID, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Void>

	@Operation(
		summary = "Enquiry for status",
		description = "Get information about an analysis - execution status and results.",
	)
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "When there is an analysis for this task in pending or running state",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = Schema(implementation = Status::class)
					)
				]
			),
			ApiResponse(
				responseCode = "303",
				description = "When analysis is completed for this task, with a Location header containing link to result",
				headers = [
					Header(
						name = HttpHeaders.LOCATION,
						description = "Location of result",
						schema = Schema(implementation = String::class, example = "/6e946e28-7c8d-11ec-90d6-0242ac120003"),
					)
				],
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Malformed UUID sent by client",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "404",
				description = "No analyze task found with this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "500",
				description = "Unexpected error",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
		]
	)
	fun enquiry(uuid: UUID, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<Status>

	@Operation(
		summary = "Get result",
		description = "Given a file UUID, return all files that contain it and were submitted after a given date,"
			.plus("ordered by submission date.")
	)
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "When there is analysis result available for this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = ArraySchema(
							schema = Schema(
								implementation = String::class,
								example = "310601e4-7c96-11ec-90d6-0242ac120003",
							)
						)
					)
				]
			),
			ApiResponse(
				responseCode = "204",
				description = "When there is analysis result available for this UUID, but is empty, which means no files contain this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Malformed UUID sent by client",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "404",
				description = "No analyze result found with this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "500",
				description = "Unexpected error",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
		]
	)
	fun getAnalyzeResult(uuid: UUID): ResponseEntity<Collection<String>>

	@Operation(
		summary = "Get result",
		description = "Given a file UUID, return all files that contain it and were submitted after a given date,"
			.plus("ordered by submission date.")
	)
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "When there is analysis result available for this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = ArraySchema(
							schema = Schema(
								implementation = String::class,
								example = "310601e4-7c96-11ec-90d6-0242ac120003",
							)
						)
					)
				],
			),
			ApiResponse(
				responseCode = "204",
				description = "When there is analysis result available for this UUID, but is empty, which means no files contain this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Malformed UUID sent by client",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "404",
				description = "No analyze result found with this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "500",
				description = "Unexpected error",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
		]
	)
	fun getContainedFiles(uuid: UUID, date: LocalDate): ResponseEntity<Collection<String>>

	@Operation(
		summary = "Get result",
		description = "Given a source file ID and a destination file ID, return the shortest path of references that"
			.plus("starts at the source file and reaches the destination file, if one exists ")
			.plus("e.g. “sourceID -> fileA -> fileB -> destinationID”.")
	)
	@ApiResponses(
		value = [
			ApiResponse(
				responseCode = "200",
				description = "When there is analysis result available for this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						schema = Schema(
							implementation = String::class,
							example = "sourceID -> fileA -> fileB -> destinationID",
						)
					)
				],
			),
			ApiResponse(
				responseCode = "204",
				description = "When there is analysis result available for this UUID, but is empty, which means no files contain this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "400",
				description = "Malformed UUID sent by client"
			),
			ApiResponse(
				responseCode = "404",
				description = "No analyze result found with this UUID",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
			ApiResponse(
				responseCode = "500",
				description = "Unexpected error",
				content = [
					Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE
					)
				]
			),
		]
	)
	fun getShortestPath(startUUID: UUID, targetUUID: UUID): ResponseEntity<String>

}
