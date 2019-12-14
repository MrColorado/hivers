package com.epita.index.core

import com.epita.models.tfidf.Vectorized

interface IndexServiceInterface {
    fun index(document: Vectorized)
    fun query(query: String): List<String>

    fun getCorpusSize(): Int
}