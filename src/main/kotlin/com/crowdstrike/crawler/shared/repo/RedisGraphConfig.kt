package com.crowdstrike.crawler.shared.repo

import com.crowdstrike.crawler.shared.prop.CrawlerRedisProperties
import com.redislabs.redisgraph.impl.api.RedisGraph
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author : Momo
 * @since : 30.01.22, Sun
 *
 **/
@Configuration
class RedisGraphConfig @Autowired constructor(
	private val crawlerRedisProperties: CrawlerRedisProperties,
) {
	@Bean
	fun redisGraph() =
		RedisGraph(crawlerRedisProperties.hostName, crawlerRedisProperties.port!!)

}
