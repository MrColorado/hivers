package com.epita.models

interface BrokerClientInterface {

    fun subscribe(topic: String, subscriber: Subscriber) : String?

    fun unsubscribe(topic: String, id: String) : Boolean

    fun <MSG_TYPE> publish(topic: String, msg: MSG_TYPE, messageType: MessageType) : Boolean

    fun listClients() : Set<Pair<String, String>>
}