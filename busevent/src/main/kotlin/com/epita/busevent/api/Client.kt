package com.epita.busevent.api

import com.epita.busevent.models.Message

class Client {
    fun subscribe(url: String, topic: String) : String? {
        return null
    }

    fun unsubscribe(id: String) : Boolean {
        return false
    }

    fun publish(topic: String, msg: Message) : Boolean {
        return false
    }
}