package com.epita.domainserver.scraper.subscribers

import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.events.NotCrawledEvent

class NotCrawledEventSubscriber(brokerClient: BrokerClientInterface, topic: String, val lambda: (id: String) -> Unit) :
    Subscriber(brokerClient, topic) {

    init {
        init()
    }

    override fun <CLASS> handle(message: CLASS) {
        val event = message as NotCrawledEvent
        lambda(event.url)
    }
}