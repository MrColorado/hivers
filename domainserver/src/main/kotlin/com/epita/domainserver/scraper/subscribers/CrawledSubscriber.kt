package com.epita.domainserver.scraper.subscribers

import com.epita.models.BrokerClientInterface
import com.epita.models.Subscriber
import com.epita.models.events.CrawledEvent

class CrawledSubscriber : Subscriber {

    val lambda: (String, List<String>) -> Unit

    constructor(brokerClient: BrokerClientInterface, topic: String, lambda:(String, List<String>) -> Unit) :
            super(brokerClient, topic) {
        init()
        this.lambda = lambda
    }
    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as CrawledEvent
        lambda(crawledEvent.url, crawledEvent.urls)
    }
}