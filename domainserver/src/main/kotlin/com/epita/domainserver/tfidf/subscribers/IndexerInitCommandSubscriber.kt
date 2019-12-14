package com.epita.domainserver.tfidf.subscribers

import com.epita.models.commands.IndexerInitCommand
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber

class IndexerInitCommandSubscriber(
    brokerClient: BrokerClientInterface,
    topic: String,
    private val lambda: (url: String) -> Unit
) : Subscriber(brokerClient, topic) {

    init {
        init()
    }

    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as IndexerInitCommand
        lambda(crawledEvent.id)
    }
}