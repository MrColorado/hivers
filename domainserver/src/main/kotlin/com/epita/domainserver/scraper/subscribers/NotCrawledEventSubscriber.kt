package com.epita.domainserver.scraper.subscribers

import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.events.NotCrawledEvent

class NotCrawledEventSubscriber : Subscriber {

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