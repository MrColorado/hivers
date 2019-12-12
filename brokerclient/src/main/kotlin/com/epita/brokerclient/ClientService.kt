package com.epita.brokerclient

import io.javalin.Javalin

class ClientService : ClientServiceInterface {

    private val url : String
    private val app : Javalin

    constructor() {
        this.app = Javalin.create()
        app.start()
        this.url = "localhost:" + app.port() + "/api/client"
    }

    fun finalize() {
        app.stop()
    }

    override fun subscribe(url: String, topic: String) : String? {
        return null
    }

    override fun unsubscribe(id: String) : Boolean {
        return false
    }

    override fun publish(topic: String, msg: Any) : Boolean {
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