package com.epita.busevent.broker


import com.epita.busevent.LoggerInterface
import com.epita.models.Message
import com.epita.models.MessageString
import com.epita.models.MessageType
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.security.MessageDigest
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.random.Random

class BusService : BusServiceInterface, LoggerInterface {
    private val urlByClient: HashMap<String, String> = HashMap()
    private val clientsByTopic: HashMap<String, HashSet<String>> = HashMap()
    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val client = HttpClient.newBuilder().build()

    private fun postJson(url: String, jsonString: String) : HttpResponse<String> {
        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(jsonString)).build()

        return client.send(request, HttpResponse.BodyHandlers.ofString())
    }

    private fun hashString(type: String, input: String): String {
        val HEX_CHARS = "0123456789ABCDEF"
        val bytes = MessageDigest
            .getInstance(type)
            .digest(input.toByteArray())
        val result = StringBuilder(bytes.size * 2)

        bytes.forEach {
            val i = it.toInt()
            result.append(HEX_CHARS[i shr 4 and 0x0f])
            result.append(HEX_CHARS[i and 0x0f])
        }
        return result.toString()
    }

    override fun subscribe(url: String, topic: String) : String? {
        val id = hashString("SHA-1", url)
        urlByClient[id] = url
        if (topic !in clientsByTopic) {
            clientsByTopic[topic] = HashSet()
        }
        clientsByTopic[topic]!!.add(id)
        return id
    }

    override fun unsubscribe(id: String, topic: String) : Boolean {
        if (topic !in clientsByTopic || !clientsByTopic[topic]!!.contains(id))
        {
            return false
        }
        clientsByTopic[topic]!!.remove(id)
        if (clientsByTopic[topic]!!.size == 0) {
            clientsByTopic.remove(topic)
        }
        return true
    }

    override fun publish(message: MessageString, json: String) {
        if (message.topic !in clientsByTopic) {
            return
        }
        if (message.msgType == MessageType.BROADCAST) {
            clientsByTopic[message.topic]!!.map {
                postJson(urlByClient[it]!!, json)
            }
        }
        else {
            val maxSize = clientsByTopic[message.topic]!!.size
            val position = Random.nextInt(maxSize)
            val id = clientsByTopic[message.topic]!!.elementAt(position)
            postJson(urlByClient[id]!!, json)
        }
    }

    override fun listClients(name: String) : Set<Pair<String, String>> {
        return clientsByTopic[name]!!.map { Pair(it, urlByClient[it]!!) }.toSet()
    }

    override fun listClients() : Set<Pair<String, String>> {
        val result = HashSet<Pair<String, String>>()
        for (clients in clientsByTopic)
            result.addAll(listClients(clients.key))
        return result
    }

    override fun getLogger(): Logger {
        return logger
    }
}