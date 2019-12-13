package com.epita.crawler

import com.epita.brokerclient.client.BrokerClient
import com.epita.crawler.impl.CrawlerSubscriber
import com.epita.hivers.core.Hivers
import com.epita.models.BrokerClientInterface
import com.epita.models.MessageType
import com.epita.models.Publisher
import com.epita.models.commands.CrawlerInitCommand
import java.util.*

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient("http://localhost:7000/"))
    }

    val publisher = Publisher(hivers.instanceOf(BrokerClientInterface::class.java))

    val crawlerId = UUID.randomUUID().toString()

    publisher.publish("crawler-init", CrawlerInitCommand(crawlerId),
        MessageType.BROADCAST, CrawlerInitCommand::class.java)

    val crawlerSubscriber = CrawlerSubscriber(
        hivers.instanceOf(BrokerClientInterface::class.java),
        "crawl-url",
        publisher
    )
}