package com.epita.indexer.impl

import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.indexer.core.IndexerServiceInterface
import com.epita.models.Topics
import com.epita.models.communications.MessageType
import com.epita.models.communications.PublisherInterface
import com.epita.models.events.IndexedEvent
import com.epita.models.tfidf.DocumentWithUrl

class IndexerService(
    private val publisher: PublisherInterface,
    private val cleaner: CleanerServiceInterface,
    private val tokenizer: TokenizerServiceInterface,
    private val vectorizer: VectorizerServiceInterface,
    private val indexerId: String
) : IndexerServiceInterface {

    override fun index(document: DocumentWithUrl) {
        val cleaned = cleaner.compute(document)
        val tokenized = tokenizer.compute(cleaned)
        val vectorized = vectorizer.compute(tokenized)
        publisher.publish(
            Topics.INDEXED_DOCUMENT_EVENT.str,
            IndexedEvent(indexerId, vectorized),
            MessageType.BROADCAST,
            IndexedEvent::class.java
        )
    }
}