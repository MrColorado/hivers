package com.epita.models.communications

data class MessageString(val msgType: MessageType, val json: String, val objectClass: String, val topic: String)