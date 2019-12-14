package com.epita.models.communications

abstract class Subscriber (val brokerClient: BrokerClientInterface, val topic: String) {
    private var id: String = ""

    protected fun init() {
        id = brokerClient.subscribe(topic, this) ?: throw RuntimeException("Can't subscribe to topic $topic")
    }

    abstract fun <CLASS> handle(message: CLASS)

    fun getId() : String {
        return  id
    }
}