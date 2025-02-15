package com.epita.models.communications

interface BrokerClientInterface {

    fun subscribe(topic: String, subscriber: Subscriber) : String?

    fun unsubscribe(topic: String, id: String) : Boolean

    fun <MSG_TYPE> publish(topic: String, msg: MSG_TYPE, messageType: MessageType, classType: Class<MSG_TYPE>) : Boolean

    fun listClients() : Set<Pair<String, String>>
}