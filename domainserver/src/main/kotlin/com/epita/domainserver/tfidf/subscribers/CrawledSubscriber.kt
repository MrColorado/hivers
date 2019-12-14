package com.epita.domainserver.tfidf.subscribers

import com.epita.models.tfidf.DocumentWithUrl
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Publisher
import com.epita.models.communications.Subscriber
import com.epita.models.events.CrawledEvent

class CrawledSubscriber : Subscriber {

    private val publisher: Publisher

    constructor(brokerClient: BrokerClientInterface, topic: String, publisher: Publisher) :
            super(brokerClient, topic) {
        init()
        this.publisher = publisher
    }
    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as CrawledEvent
        val document = DocumentWithUrl(crawledEvent.url, crawledEvent.document)
    }
}