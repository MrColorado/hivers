package com.epita.models

class Publisher(val brokerClient: BrokerClient) {

    fun <MSG_TYPE> publish(topic: String, msg: MSG_TYPE, messageType: MessageType) : Boolean {
        return brokerClient.publish(topic, msg, messageType)
    }
}