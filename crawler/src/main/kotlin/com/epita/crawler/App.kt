package com.epita.crawler

import com.epita.brokerclient.client.BrokerClient
import com.epita.crawler.subscribers.CrawlerSubscriber
import com.epita.hivers.core.Hivers
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.Publisher
import com.epita.models.commands.CrawlerInitCommand
import java.util.*

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient("http://localhost:7000/"))
    }

    val publisher =
        Publisher(hivers.instanceOf(BrokerClientInterface::class.java))

    val crawlerId = UUID.randomUUID().toString()

    CrawlerSubscriber(
        hivers.instanceOf(BrokerClientInterface::class.java),
        "crawl-url",
        publisher
    )

    publisher.publish("crawler-init-command", CrawlerInitCommand(crawlerId),
        MessageType.BROADCAST, CrawlerInitCommand::class.java)
}