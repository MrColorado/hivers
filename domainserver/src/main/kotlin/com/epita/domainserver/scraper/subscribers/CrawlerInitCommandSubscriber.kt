package com.epita.domainserver.scraper.subscribers

import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.commands.CrawlerInitCommand

class CrawlerInitCommandSubscriber(
    brokerClient: BrokerClientInterface,
    topic: String,
    val lambda: (id: String) -> Unit
) : Subscriber(brokerClient, topic) {

    init {
        init()
    }

    override fun <CLASS> handle(message: CLASS) {
        val command = message as CrawlerInitCommand
        lambda(command.id)
    }
}