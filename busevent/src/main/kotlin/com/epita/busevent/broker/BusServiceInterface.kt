package com.epita.busevent.broker

import com.epita.models.communications.MessageString


interface BusServiceInterface {
    fun subscribe(url: String, topic: String) : String?

    fun unsubscribe(id: String, topic: String) : Boolean

    fun publish(message: MessageString, json: String)

    fun listClients(name: String) : Set<Pair<String, String>>

    fun listClients() : Set<Pair<String, String>>
}
