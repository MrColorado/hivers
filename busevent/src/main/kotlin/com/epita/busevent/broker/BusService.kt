package com.epita.busevent.broker

class BusService : Bus {
    private val urlByClient: MutableMap<String, String> = HashMap()
    private val clientsByTopic: MutableMap<String, MutableList<String>> = HashMap()

    override fun subscribe(url: String, topic: String) : String? {
        return null
    }

    override fun unsubscribe(id: String, topic: String) : Boolean {
        return false
    }

    override fun createTopic(name: String) : Boolean {
        return false
    }

    override fun deleteTopic(name: String) : Boolean {
        return false
    }

    override fun listClients() : List<String> {
        return emptyList()
    }
}