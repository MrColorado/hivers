package com.epita.tfidf.indexer.core

import com.epita.tfidf.models.Vectorized

interface Index {
    fun insert(keyword: String, document: Vectorized)
    fun search(keyword: String) : List<Vectorized>?
}