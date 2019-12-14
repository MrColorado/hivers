package com.epita.index.subscribers

import com.epita.index.core.IndexServiceInterface
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber
import com.epita.models.events.IndexedEvent

class IndexerSubscriber : Subscriber {

    private val index: IndexServiceInterface

    constructor(brokerClient: BrokerClientInterface, topic: String, index: IndexServiceInterface) :
            super(brokerClient, topic) {
        init()
        this.index = index
    }

    override fun <CLASS> handle(message: CLASS) {
        val document = message as IndexedEvent
        val vectorized = document.document
        index.index(vectorized)
    }
}