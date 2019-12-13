package com.epita.brokerclient.models

import com.epita.models.MessageType

data class MessagePublish(val content: String, val topic: String, val type: MessageType)