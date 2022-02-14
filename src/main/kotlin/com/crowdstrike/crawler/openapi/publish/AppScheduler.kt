package com.crowdstrike.crawler.openapi.publish

import com.crowdstrike.crawler.shared.util.toNewTask
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author : Mohammad <mohammad.saeedi@visual-meta.com>
 * @since : 08.02.22, Tue
 *
 **/
@Component
class AppScheduler @Autowired constructor(
	private val taskPublisher: TaskPublisher
) {

	@Scheduled(fixedDelay = 5000)
	fun publish() =
		taskPublisher.publish(UUID.randomUUID().toNewTask())

}
