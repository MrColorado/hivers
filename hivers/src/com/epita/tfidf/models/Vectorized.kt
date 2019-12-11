package com.epita.tfidf.models

data class Vectorized(override val url: String, val keywords: List<Keyword>) : Document
