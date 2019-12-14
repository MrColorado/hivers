package com.epita.domainserver.tfidf.subscribers

import com.epita.models.tfidf.DocumentWithUrl
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Publisher
import com.epita.models.communications.Subscriber
import com.epita.models.events.CrawledEvent

class RegisterCrawledSubscriber : Subscriber {

    private val lambda : (document: DocumentWithUrl) -> Unit

    constructor(brokerClient: BrokerClientInterface, topic: String, lambda:(document: DocumentWithUrl) -> Unit) :
            super(brokerClient, topic) {
        init()
        this.lambda = lambda
    }

    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as CrawledEvent
        val document = DocumentWithUrl(crawledEvent.url, crawledEvent.document)
        lambda(document)
    }
}