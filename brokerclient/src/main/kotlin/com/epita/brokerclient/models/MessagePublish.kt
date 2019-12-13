package com.epita.brokerclient.models

data class MessagePublish(val content: String, val topic: String, val type: MessageType)