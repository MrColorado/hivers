package com.epita.busevent.models

data class Message(val content: ByteArray, val topic: String, val type: MessageType)