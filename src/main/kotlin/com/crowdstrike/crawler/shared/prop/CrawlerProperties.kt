package com.crowdstrike.crawler.shared.prop

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author : Momo
 * @since : 23.01.22, Sun
 *
 **/
@Component
@ConfigurationProperties(prefix = "crawler.config", ignoreUnknownFields = false)
class CrawlerProperties {
	var pathToDirectory = ""
	var maxTaskSize:Int? = null
	var chunkSize:Int? = null
}
