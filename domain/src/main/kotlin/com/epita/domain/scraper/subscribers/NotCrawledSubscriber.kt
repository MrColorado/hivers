package com.epita.domain.scraper.subscribers

import com.epita.models.BrokerClientInterface
import com.epita.models.Subscriber
import com.epita.models.events.NotCrawledEvent

class NotCrawledSubscriber : Subscriber {

    val lambda : (id: String) -> Unit

    constructor(brokerClient: BrokerClientInterface, topic: String, lambda: (id: String) -> Unit) :
            super(brokerClient, topic) {
        init()
        this.lambda = lambda
    }

    override fun <CLASS> handle(message: CLASS) {
        val event = message as NotCrawledEvent
        lambda(event.url)
    }
}