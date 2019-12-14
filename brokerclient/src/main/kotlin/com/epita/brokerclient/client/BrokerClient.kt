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

    private val subscribers : MutableMap<String, MutableList<Subscriber>> = HashMap()

    override val getMessage: (Context) -> Unit = {
        try {
            val body = it.body()

            val mapper = jacksonObjectMapper()
            val msg = mapper.readValue<MessageString>(body)

            var subscriberList = subscribers.get(msg.topic)
            if (subscriberList == null)
                subscriberList = arrayListOf()

            val obj = mapper.readValue(msg.json, Class.forName(msg.objectClass))

            subscriberList?.forEach {
                try {
                    it.handle(obj)
                }
                catch (e: Exception) {
                    println(e)
                }
            }
        } catch (e: Exception) {
            logger.error(e.message)
        }
    }

    init {
        val port = ServerSocket(0).use { it.localPort }
        this.url = "http://localhost:$port/api/client"
        try {
            this.app.start(port)
                .post("api/client", this.getMessage)
        } catch (e: Exception) {
            logger.error("Cannot start broker client app at `$url`")
            logger.error(e.message)
        }
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

    private fun postJsonAsync(endpoint: String, jsonString: String) {
        val client = HttpClient.newBuilder().build()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(serverUrl + endpoint))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonString)).build()
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
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
        logger.info("${subscriber.javaClass.name} subscribes ${subscriber.topic}")

        var subscriberList = subscribers[subscriber.topic]
        if (null == subscriberList)
            subscriberList = arrayListOf(subscriber)
        else
            subscriberList.add(subscriber)
        subscribers[subscriber.topic] = subscriberList
        val mapper = jacksonObjectMapper()
        val message = mapper.writeValueAsString(UrlWithTopic(url, subscriber.topic))
        val res =  postJson("subscribe", message)

        val id = res.body()
        logger.info("subscriber id for ${subscriber.topic} = $id")

        return id
    }

    override fun unsubscribe(topic: String, id: String) : Boolean {
        logger.info("$id unsubscribes $topic")

        val subscriberList = subscribers.get(topic)
        subscriberList?.filter { id != it.getId()}
        if (null == subscriberList || subscriberList.isEmpty()) {
            subscribers.remove(topic)
        } else {
            subscribers[topic] = subscriberList
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

        logger.info("publish $topic")

        val res =  postJsonAsync("publish", message)
        return true
    }

    override fun listClients() : Set<Pair<String, String>> {
        val res = getJson("clients")
        val clients = res.body()
        val mapper = jacksonObjectMapper()

        val state = mapper.readValue<Set<Pair<String, String>>>(clients)
        return state
    }
}