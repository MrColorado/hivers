package com.epita.models.communications

import com.epita.models.exceptions.NotSubscribableException

abstract class Subscriber(val brokerClient: BrokerClientInterface, val topic: String) {
    var id = ""
        private set

    protected fun init() {
        id = brokerClient.subscribe(topic, this) ?: throw NotSubscribableException(topic)
    }

    abstract fun <CLASS> handle(message: CLASS)
}