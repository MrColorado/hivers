package com.epita.tfidf.models

data class Cleaned(override val url: String, val title: String, val text: String) : Document