package com.epita.tfidf.indexer.impl

import com.epita.tfidf.indexer.core.Index
import com.epita.tfidf.indexer.core.Indexer
import com.epita.tfidf.models.Vectorized

class BasicIndexer : Indexer {

    private val index: Index

    constructor(index: Index) {
        this.index = index
    }

    override fun indexDocument(document: Vectorized) {
        for((key, _) in document.keywords) {
            index.insert(key, document)
        }
    }
}