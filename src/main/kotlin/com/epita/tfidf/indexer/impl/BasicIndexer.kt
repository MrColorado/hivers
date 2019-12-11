package com.epita.tfidf.indexer.impl

import com.epita.tfidf.indexer.core.Index
import com.epita.tfidf.indexer.core.Indexer
import com.epita.tfidf.models.Vectorized

class BasicIndexer(private val index: Index) : Indexer {

    override fun indexDocument(document: Vectorized) {
        index.index(document)
        document.keywords.forEach { (key, _) -> index.insert(key, document) }
    }
}