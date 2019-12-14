package com.epita.crawler.subscribers

import com.epita.crawler.core.CrawlerServiceInterface
import com.epita.models.commands.CrawlerCommand
import com.epita.models.communications.*
import com.epita.models.events.CrawledEvent
import com.epita.models.events.NotCrawledEvent
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import java.lang.Exception
import java.util.regex.Pattern


class CrawlUrlCommandSubscriber : Subscriber {

    private val service: CrawlerServiceInterface

    constructor(brokerClient: BrokerClientInterface, topic: String, service: CrawlerServiceInterface) : super(brokerClient, topic) {
        init()
        this.service = service
    }

    override fun <CLASS> handle(message: CLASS) {
        val crawlerCommand = message as CrawlerCommand
        service.crawl(crawlerCommand.url)
    }
}