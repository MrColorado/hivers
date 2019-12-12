package com.epita.busevent.api

import com.epita.busevent.models.Message

class Server {

    fun pushMessage(topic: String, msg: Message) : Boolean {
        return false
    }

    fun createTopic(name: String) : Boolean {
        return false
    }

    fun deleteTopic(name: String) : Boolean {
        return false
    }

    fun listClients() : List<String> {

        return emptyList()
    }
}