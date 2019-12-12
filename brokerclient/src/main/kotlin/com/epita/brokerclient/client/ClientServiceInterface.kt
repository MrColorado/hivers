package com.epita.brokerclient.client

import com.epita.brokerclient.models.MessageType


interface ClientServiceInterface {
    fun subscribe(url: String, topic: String) : String?

    fun unsubscribe(id: String) : Boolean

    fun publish(topic: String, msg: Any, msgType: MessageType) : Boolean

    fun createTopic(name: String) : Boolean

    fun deleteTopic(name: String) : Boolean

    fun listClients() : Set<Pair<String, String>>
}