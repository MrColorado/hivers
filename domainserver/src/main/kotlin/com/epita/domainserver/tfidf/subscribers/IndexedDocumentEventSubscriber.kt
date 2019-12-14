package com.epita.domainserver.tfidf.subscribers

import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.events.IndexedEvent

class IndexedDocumentEventSubscriber(
    brokerClient: BrokerClientInterface,
    topic: String,
    val lambda: (id: String) -> Unit
) : Subscriber(brokerClient, topic) {

    init {
        init()
    }

    override fun <CLASS> handle(message: CLASS) {
        val indexedEvent = message as IndexedEvent
        lambda(indexedEvent.id)
    }

}