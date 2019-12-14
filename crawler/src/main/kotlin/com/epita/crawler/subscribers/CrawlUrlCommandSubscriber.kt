package com.epita.crawler.subscribers

import com.epita.crawler.core.CrawlerServiceInterface
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.commands.CrawlerCommand


class CrawlUrlCommandSubscriber(
    brokerClient: BrokerClientInterface,
    topic: String,
    private val service: CrawlerServiceInterface
) : Subscriber(brokerClient, topic) {

    init {
        init()
    }

    override fun <CLASS> handle(message: CLASS) {
        val crawlerCommand = message as CrawlerCommand
        service.crawl(crawlerCommand.url)
    }
}