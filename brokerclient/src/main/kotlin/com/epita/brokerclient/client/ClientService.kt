package com.epita.brokerclient.client

import com.epita.brokerclient.models.UrlWithTopic
import com.epita.brokerclient.models.MessageType
import com.epita.brokerclient.models.Name
import com.epita.brokerclient.models.*
import com.epita.hivers.core.Hivers
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.Javalin
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


class ClientService(private val serverUrl: String) : ClientServiceInterface {

    private val url : String
    private val app : Javalin
    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    private val hivers = Hivers {
        bean(ClientControllerInterface::class.java, ClientController())
    }

    private val clientController = hivers.instanceOf(ClientControllerInterface::class.java)

    init {
        this.app = Javalin.create()
        this.app.start(7002)
            .get("api/client", clientController.getMessage)
        this.url = "http://localhost:" + app.port() + "/api/client"
    }

    fun finalize() {
        app.stop()
    }

    private fun getJson(endpoint: String) : HttpResponse<String> {
        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(serverUrl + endpoint))
            .header("Content-Type", "application/json")
            .GET().build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response
    }

    private fun postJson(endpoint: String, jsonString: String) : HttpResponse<String> {
        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(serverUrl + endpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonString)).build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response
    }

    private fun deleteJson(endpoint: String, jsonString: String) : HttpResponse<String> {
        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(serverUrl + endpoint))
            .header("Content-Type", "application/json")
            .method("DELETE", HttpRequest.BodyPublishers.ofString(jsonString)).build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        return response
    }

    override fun subscribe(topic: String) : String? {
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(UrlWithTopic(url, topic))
        val res =  postJson("subscribe", message)
        return res.body()
    }

    override fun unsubscribe(id: String) : Boolean {
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(Id(id))
        val res =  postJson("unsubscribe", message)
        return res.statusCode() == 200
    }

    override fun publish(topic: String, msg: Any, msgType: MessageType) : Boolean {
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(MessagePublish(topic, msg, msgType))
        val res =  postJson("unsubscribe", message)
        return res.statusCode() == 200
    }

    override fun createTopic(name: String) : Boolean {
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(Name(name))
        val res =  postJson("topics", message)
        return res.statusCode() == 200
    }

    override fun deleteTopic(name: String) : Boolean {
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(Name(name))
        val res =  deleteJson("topics", message)
        return res.statusCode() == 200
    }

    override fun listClients() : Set<Pair<String, String>> {
        val res = getJson("clients")
        val clients = res.body()
        val mapper = jacksonObjectMapper()

        val state = mapper.readValue<Set<Pair<String, String>>>(clients)
        return state
    }
}