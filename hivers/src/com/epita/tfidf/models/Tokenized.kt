package com.epita.tfidf.models

data class Tokenized(override val url: String, val tokens: List<String>) : Document