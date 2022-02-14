package com.crowdstrike.crawler.openapi

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @author : Momo
 * @since : 24.01.22, Mon
 *
 **/
@ExtendWith(MockKExtension::class)
class EnquiryTest {
	@Test
	fun `assure receiving 400, when UUID is invalid`() {
	}

	@Test
	fun `assure receiving 404, when no status found with this UUID`() {
	}

	@Test
	fun `assure receiving 303, when status is COMPLETE`() {

	}

	@Test
	fun `assure setting Location header , when status is 303`() {

	}

	@Test
	fun `assure receiving 200 with status, when status is PROCESSING or NEW`() {

	}
}
