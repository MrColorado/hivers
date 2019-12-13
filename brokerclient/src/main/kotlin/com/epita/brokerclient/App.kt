package com.epita.brokerclient

import com.epita.brokerclient.client.BrokerClient
import com.epita.models.BrokerClientInterface
import com.epita.models.MessageType
import com.epita.models.Publisher
import com.epita.models.Subscriber
import com.epita.models.test.TestObject

class TestSubscriber(brokerClient: BrokerClientInterface, topic: String)
    : Subscriber(brokerClient, topic) {
    override fun <CLASS> handle(message: CLASS) {
        println((message as TestObject).msg)
    }
}

fun main() {
    val brokerClient = BrokerClient("http://localhost:7000/")

    val subscriber = TestSubscriber(brokerClient, "toto")
    val id = brokerClient.subscribe("toto", subscriber)
    println(id)

    val publisher = Publisher(brokerClient)

    publisher.publish("toto", TestObject( "VINCENT LE RAGEUX"), MessageType.ONCE, TestObject::class.java)

    val clients = brokerClient.listClients()
}