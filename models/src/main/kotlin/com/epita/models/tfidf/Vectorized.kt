package com.epita.models.tfidf

data class Vectorized(override val url: String, val keywords: Map<String, KeywordData>) : Document
