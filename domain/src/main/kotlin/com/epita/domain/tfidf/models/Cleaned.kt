package com.epita.domain.tfidf.models

data class Cleaned(override val url: String, val text: String) : Document