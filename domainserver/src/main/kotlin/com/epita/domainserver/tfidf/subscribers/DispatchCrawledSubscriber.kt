package com.epita.domainserver.tfidf.subscribers

import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.Subscriber

class DispatchCrawledSubscriber : Subscriber {

    val lambda: () -> Unit

    constructor(brokerClient: BrokerClientInterface, topic: String, lambda:() -> Unit) :
            super(brokerClient, topic) {
        init()
        this.lambda = lambda
    }

    override fun <CLASS> handle(message: CLASS) {

        lambda()
    }

}