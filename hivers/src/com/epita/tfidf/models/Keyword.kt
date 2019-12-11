package com.epita.tfidf.models

data class Keyword(val word: String, val frequency: Double, val positions: List<Int>)
