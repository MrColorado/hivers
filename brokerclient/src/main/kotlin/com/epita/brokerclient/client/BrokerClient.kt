package com.epita.brokerclient.client

import com.epita.brokerclient.models.UrlWithTopic
import com.epita.brokerclient.models.*
import com.epita.models.Constants
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageString
import com.epita.models.communications.MessageType
import com.epita.models.communications.Subscriber
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.javalin.Javalin
import io.javalin.http.Context
import org.slf4j.LoggerFactory
import java.net.ServerSocket
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class BrokerClient(private val serverUrl: String) : BrokerClientInterface, ClientControllerInterface {
    private val url : String
    private val app : Javalin = Javalin.create { config ->
        config.requestCacheSize = Constants.maxBodySize
    }

    private val logger = LoggerFactory.getLogger(this.javaClass.name)

    private val subscribers : MutableMap<String, Subscriber> = HashMap()

    override val getMessage: (Context) -> Unit = {
        val body = it.body()

        val mapper = jacksonObjectMapper()
        val msg = mapper.readValue<MessageString>(body)

        val subscriber = subscribers.get(msg.topic)


        val obj = mapper.readValue(msg.json, Class.forName(msg.objectClass))
        subscriber?.handle(obj)
    }

    init {
        val port = ServerSocket(0).use { it.localPort }
        this.app.start(port)
            .post("api/client", this.getMessage)
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

    override fun subscribe(topic: String, subscriber: Subscriber) : String? {
        subscribers[topic] = subscriber
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(UrlWithTopic(url, topic))
        val res =  postJson("subscribe", message)
        return res.body()
    }

    override fun unsubscribe(topic: String, id: String) : Boolean {
        val subscriber = subscribers.get(topic)
        if (subscriber?.getId() == id) {
            subscribers.remove(topic)
        } else {
            return false
        }

        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(Id(id))
        val res =  postJson("unsubscribe", message)
        return res.statusCode() == 200
    }

    override fun <MSG_TYPE> publish(topic: String, msg: MSG_TYPE, messageType: MessageType, classType: Class<MSG_TYPE>) : Boolean {
        val mapper = jacksonObjectMapper()
        val content = mapper.writeValueAsString(msg)
        val message = mapper.writeValueAsString(
            MessageString(
                messageType,
                content,
                classType.name,
                topic
            )
        )
        val res =  postJson("publish", message)
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