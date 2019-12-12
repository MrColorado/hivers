package com.epita.busevent.broker

interface Bus {
    fun subscribe(url: String, topic: String) : String?

    fun unsubscribe(id: String, topic: String) : Boolean

    fun createTopic(name: String) : Boolean

    fun deleteTopic(name: String) : Boolean

    fun listClients() : List<String>
}
