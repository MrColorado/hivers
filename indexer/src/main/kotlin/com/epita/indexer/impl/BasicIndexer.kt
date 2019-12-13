package com.epita.indexer.impl

import com.epita.indexer.core.Indexer
import com.epita.tfidf.models.Vectorized

class BasicIndexer(private val index: Index) : Indexer {

    override fun indexDocument(document: Vectorized) {
        index.index(document)
        document.keywords.forEach { (key, _) -> index.insert(key, document) }
    }
}