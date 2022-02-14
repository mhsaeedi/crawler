package com.crowdstrike.crawler.openapi

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @author : Momo
 * @since : 23.01.22, Sun
 */
@ExtendWith(MockKExtension::class)
internal class AnalyzeResourceTest {
	@Test
	fun `assure receiving 400, when UUID is invalid`() {

	}

	@Test
	fun `assure receiving 404, when no result found with this UUID`() {
	}

	@Test
	fun `assure receiving 204, when result exist but is empty`() {
	}

	@Test
	fun `assure receiving 200 with result, when result is not empty`() {
	}

}
