package com.epita.brokerclient.models

data class MessagePublish(val topic: String, val msg: Any, val msgType: MessageType)