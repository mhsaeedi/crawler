package com.crowdstrike.crawler.shared.prop

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

/**
 * @author : Momo
 * @since : 23.01.22, Sun
 *
 **/
@Component
@ConfigurationProperties(prefix = "crawler.redis", ignoreUnknownFields = false)
class CrawlerRedisProperties {
	var hostName = ""
	var port:Int? = null
	var database:Int? = null
	var password = ""

}
