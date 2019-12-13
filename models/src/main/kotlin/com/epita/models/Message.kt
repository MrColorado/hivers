package com.epita.models

data class Message(val msgType: MessageType, val json: String, val objectClass: Class<*>, val topic: String)