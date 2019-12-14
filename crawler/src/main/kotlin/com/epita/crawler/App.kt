package com.epita.crawler

import com.epita.brokerclient.client.BrokerClient
import com.epita.crawler.core.CrawlerServiceInterface
import com.epita.crawler.impl.CrawlerService
import com.epita.crawler.subscribers.CrawlUrlCommandSubscriber
import com.epita.hivers.core.Hivers
import com.epita.models.Constants
import com.epita.models.Topics
import com.epita.models.commands.CrawlerInitCommand
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.Publisher
import com.epita.models.communications.PublisherInterface
import java.util.*

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient(Constants.serverUrl))
        bean(PublisherInterface::class.java, Publisher(instanceOf(BrokerClientInterface::class.java)))
        bean(CrawlerServiceInterface::class.java, CrawlerService(instanceOf(PublisherInterface::class.java)))
    }

    val publisher = hivers.instanceOf(PublisherInterface::class.java)

    val crawlerId = UUID.randomUUID().toString()

    CrawlUrlCommandSubscriber(
        hivers.instanceOf(BrokerClientInterface::class.java),
        Topics.CRAWL_URL_COMMAND.str,
        hivers.instanceOf(CrawlerServiceInterface::class.java)
    )

    publisher.publish(
        Topics.CRAWLER_INIT_COMMAND.str, CrawlerInitCommand(crawlerId),
        MessageType.BROADCAST, CrawlerInitCommand::class.java
    )
}