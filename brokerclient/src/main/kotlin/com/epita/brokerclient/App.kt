package com.epita.brokerclient

import com.epita.brokerclient.client.BrokerClient
import com.epita.models.BrokerClientInterface
import com.epita.models.Subscriber


class TestSubscriber(brokerClient: BrokerClientInterface, topic: String)
    : Subscriber(brokerClient, topic) {
    override fun <CLASS> handle(message: CLASS) {

        println(message as String)
    }
}

fun main() {
    val brokerClient = BrokerClient("http://localhost:7000/")

    val subscriber = TestSubscriber(brokerClient, "toto")
    val id = brokerClient.subscribe("toto", subscriber)
    println(id)

    val clients = brokerClient.listClients()
}