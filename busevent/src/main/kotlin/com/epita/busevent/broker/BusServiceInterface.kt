package com.epita.busevent.broker

interface BusServiceInterface {
    fun subscribe(url: String, topic: String) : String?

    fun unsubscribe(id: String, topic: String) : Boolean

    fun createTopic(name: String) : Boolean

    fun deleteTopic(name: String) : Boolean

    fun listClients(name: String) : Set<Pair<String, String>>

    fun listClients() : Set<Pair<String, String>>
}
