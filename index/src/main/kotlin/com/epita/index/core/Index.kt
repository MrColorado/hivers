package com.epita.index.core

import com.epita.tfidf.models.DocumentCosine
import com.epita.tfidf.models.Vectorized

interface Index {
    fun index(document: Vectorized)
    fun insert(keyword: String, document: Vectorized)
    fun search(query: Vectorized) : List<DocumentCosine>

    fun getCorpusSize(): Int
}