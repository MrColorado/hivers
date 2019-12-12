package com.epita.brokerclient.client

import com.epita.brokerclient.LoggerInterface
import com.epita.brokerclient.models.MessageType
import com.epita.hivers.core.Hivers
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.Javalin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class ClientService(private val serverUrl: String) : ClientServiceInterface, LoggerInterface {

    private val url : String
    private val app : Javalin
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    private val hivers = Hivers {
        bean(ClientControllerInterface::class.java, ClientController())
    }

    private val clientController = hivers.instanceOf(ClientControllerInterface::class.java)

    init {
        this.app = Javalin.create()
            .get("api//client", clientController.getMessage)
        this.url = "localhost:" + app.port() + "/api/client"
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

    override fun getLogger(): Logger {
        return logger
    }
}