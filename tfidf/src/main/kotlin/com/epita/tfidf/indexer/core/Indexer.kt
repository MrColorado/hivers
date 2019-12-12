package com.epita.tfidf.indexer.core

import com.epita.tfidf.models.Vectorized

interface Indexer {
    fun indexDocument(document: Vectorized)
}