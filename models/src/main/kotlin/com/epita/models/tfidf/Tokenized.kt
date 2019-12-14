package com.epita.models.tfidf

data class Tokenized(override val url: String, val tokens: List<String>) : Document