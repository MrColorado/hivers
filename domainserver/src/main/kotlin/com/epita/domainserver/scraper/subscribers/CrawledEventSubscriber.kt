package com.epita.domainserver.scraper.subscribers

import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.events.CrawledEvent

class CrawledEventSubscriber(
    brokerClient: BrokerClientInterface,
    topic: String,
    val lambda: (String, List<String>) -> Unit
) : Subscriber(brokerClient, topic) {

    init {
        init()
    }
    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as CrawledEvent
        lambda(crawledEvent.url, crawledEvent.urls)
    }
}