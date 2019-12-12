package com.epita.brokerclient

interface ClientServiceInterface {
    fun subscribe(url: String, topic: String) : String?

    fun unsubscribe(id: String) : Boolean

    fun publish(topic: String, msg: Any) : Boolean

    fun createTopic(name: String) : Boolean

    fun deleteTopic(name: String) : Boolean

    fun listClients() : List<String>
}