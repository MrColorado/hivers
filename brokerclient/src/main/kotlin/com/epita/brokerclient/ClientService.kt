package com.epita.brokerclient

import com.epita.brokerclient.models.MessageType
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.Javalin

class ClientService : ClientServiceInterface {


    private val url : String
    private val app : Javalin
    private val serverUrl: String

    constructor(serverUrl: String) {
        this.app = Javalin.create()
        app.start()
        this.url = "localhost:" + app.port() + "/api/client"
        this.serverUrl = serverUrl
    }

    fun finalize() {
        app.stop()
    }

    override fun subscribe(url: String, topic: String) : String? {
        val id = khttp.post(serverUrl + "subscribe", data = mapOf("url" to url, "topic" to topic)).text
        return id
    }

    override fun unsubscribe(id: String) : Boolean {
        val code = khttp.post(serverUrl + "unsubscribe", data = mapOf("id" to id)).statusCode
        return code == 200
    }

    override fun publish(topic: String, msg: Any, msgType: MessageType) : Boolean {
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(msg)
        val code = khttp.post(serverUrl + "topics", data = mapOf("topic" to topic, "msg" to message, "msgType" to msgType.toString())).statusCode
        return code == 200
    }

    override fun createTopic(name: String) : Boolean {
        val code = khttp.post(serverUrl + "topics", data = mapOf("name" to name)).statusCode
        return code == 200
    }

    override fun deleteTopic(name: String) : Boolean {
        val code = khttp.delete(serverUrl + "topics", data = mapOf("name" to name)).statusCode
        return code == 200
    }

    override fun listClients() : Set<Pair<String, String>> {
        val clients = khttp.get(serverUrl + "clients").text
        val mapper = jacksonObjectMapper()

        val state = mapper.readValue<Set<Pair<String, String>>>(clients)
        return state
    }
}