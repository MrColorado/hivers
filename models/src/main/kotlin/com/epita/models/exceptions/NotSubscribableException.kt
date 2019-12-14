package com.epita.models.exceptions

import java.lang.RuntimeException

class NotSubscribableException(val topic: String) : RuntimeException("Can't subscribe to topic $topic")