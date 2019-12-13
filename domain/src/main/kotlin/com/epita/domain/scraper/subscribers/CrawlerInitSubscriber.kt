package com.epita.domain.scraper.subscribers

import com.epita.models.BrokerClientInterface
import com.epita.models.Subscriber
import com.epita.models.commands.CrawlerInitCommand

class CrawlerInitSubscriber : Subscriber {

    val lambda : (id: String) -> Unit

    constructor(brokerClient: BrokerClientInterface, topic: String, lambda: (id: String) -> Unit) :
            super(brokerClient, topic) {
        init()
        this.lambda = lambda
    }

    override fun <CLASS> handle(message: CLASS) {
        val command = message as CrawlerInitCommand
        lambda(command.id)
    }
}