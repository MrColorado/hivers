package com.epita.domainserver.tfidf.subscribers

import com.epita.models.commands.IndexerInitCommand
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber

class RegisterIndexerSubscriber : Subscriber {

    private val lambda: (url: String) -> Unit

    constructor(brokerClient: BrokerClientInterface, topic: String, lambda:(url: String) -> Unit) :
            super(brokerClient, topic) {
        init()
        this.lambda = lambda
    }

    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as IndexerInitCommand
        lambda(crawledEvent.id)
    }
}