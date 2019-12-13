package com.epita.indexer.core

import com.epita.tfidf.models.Vectorized

interface Indexer {
    fun indexDocument(document: Vectorized)
}