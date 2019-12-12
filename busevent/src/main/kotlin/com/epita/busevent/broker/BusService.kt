package com.epita.busevent.broker


import com.epita.busevent.models.Message
import java.security.MessageDigest
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class BusService : BusServiceInterface {

    private val urlByClient: MutableMap<String, String> = HashMap()
    private val clientsByTopic: MutableMap<String, MutableSet<String>> = HashMap()

    private val queueTopic: Deque<Message> = ArrayDeque()

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
            return null
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
        return true
    }

    override fun createTopic(name: String) : Boolean {
        if (name in clientsByTopic) {
            return false
        }
        clientsByTopic[name] = HashSet()
        return true
    }

    override fun deleteTopic(name: String) : Boolean {
        if (name !in clientsByTopic) {
            return false
        }
        clientsByTopic.remove(name)
        return true
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
}