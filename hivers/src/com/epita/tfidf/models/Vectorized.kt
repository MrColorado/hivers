package com.epita.tfidf.models

data class Vectorized(override val url: String, val keywords: Map<String, KeywordData>) : Document
