package com.epita.models

abstract class Subscriber(val brokerClient: BrokerClient, val topic: String) {
    private var id: String = ""

    protected fun init() {
        id = brokerClient.subscribe(topic, this) ?: throw RuntimeException("Can't subscribe to topic $topic")
    }

    abstract fun handle(messageType: MessageType)

    fun getId() : String {
        return  id
    }
}