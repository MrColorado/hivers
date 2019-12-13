package com.epita.models

class Publisher(val brokerClient: BrokerClientInterface) {

    fun <MSG_TYPE> publish(topic: String, msg: MSG_TYPE, messageType: MessageType, classType: Class<MSG_TYPE>) : Boolean {
        return brokerClient.publish(topic, msg, messageType, classType)
    }
}