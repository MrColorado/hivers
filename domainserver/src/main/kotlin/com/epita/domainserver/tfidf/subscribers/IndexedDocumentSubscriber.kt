package com.epita.domainserver.tfidf.subscribers

import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.events.IndexedEvent

class IndexedDocumentSubscriber : Subscriber {

    val lambda: (id: String) -> Unit

    constructor(brokerClient: BrokerClientInterface, topic: String, lambda:(id: String) -> Unit) :
            super(brokerClient, topic) {
        init()
        this.lambda = lambda
    }

    override fun <CLASS> handle(message: CLASS) {
        val indexedEvent = message as IndexedEvent
        lambda(indexedEvent.id)
    }

}