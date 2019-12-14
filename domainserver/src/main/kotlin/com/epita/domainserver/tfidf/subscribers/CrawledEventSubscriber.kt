package com.epita.domainserver.tfidf.subscribers

import com.epita.models.tfidf.DocumentWithUrl
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Publisher
import com.epita.models.communications.Subscriber
import com.epita.models.events.CrawledEvent

class CrawledEventSubscriber(
    brokerClient: BrokerClientInterface,
    topic: String,
    private val lambda: (document: DocumentWithUrl) -> Unit
) : Subscriber(brokerClient, topic) {

    init {
        init()
    }

    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as CrawledEvent
        val document = DocumentWithUrl(crawledEvent.url, crawledEvent.document)
        lambda(document)
    }
}