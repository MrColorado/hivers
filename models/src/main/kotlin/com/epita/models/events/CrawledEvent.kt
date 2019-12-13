package com.epita.models.events

data class CrawledEvent(val url: String, val document: String, val urls: List<String>)