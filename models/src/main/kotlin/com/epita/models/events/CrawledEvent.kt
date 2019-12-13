package com.epita.models.events

import org.jsoup.nodes.Document

data class CrawledEvent(val url: String, val document: String, val urls: List<String>)