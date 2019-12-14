package com.epita.models.communications

data class Message(val msgType: MessageType, val json: String, val objectClass: Class<*>, val topic: String)
data class MessageString(val msgType: MessageType, val json: String, val objectClass: String, val topic: String)