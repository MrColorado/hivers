package com.epita.tfidf.indexer.core

import com.epita.tfidf.models.Vectorized

interface Index {
    fun index(document: Vectorized)
    fun insert(keyword: String, document: Vectorized)
    fun search(query: Vectorized) : Set<Vectorized>

    fun getCorpusSize(): Int
}