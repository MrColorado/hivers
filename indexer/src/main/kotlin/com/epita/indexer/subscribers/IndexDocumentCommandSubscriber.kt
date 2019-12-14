package com.epita.indexer.subscribers

import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.models.commands.IndexCommand
import com.epita.models.communications.*
import com.epita.models.events.IndexedEvent
import com.epita.models.tfidf.DocumentWithUrl

class IndexDocumentCommandSubscriber : Subscriber {

    private val publisher: PublisherInterface
    private val cleaner: CleanerServiceInterface
    private val tokenizer: TokenizerServiceInterface
    private val vectorizer: VectorizerServiceInterface
    private val indexerId: String

    constructor(brokerClient: BrokerClientInterface, topic: String, publisher: PublisherInterface,
                cleaner: CleanerServiceInterface, tokenizer: TokenizerServiceInterface,
                vectorizer: VectorizerServiceInterface, id: String) : super(brokerClient, topic) {
        init()
        this.publisher = publisher
        this.cleaner = cleaner
        this.tokenizer = tokenizer
        this.vectorizer = vectorizer
        this.indexerId = id
    }

    override fun <CLASS> handle(message: CLASS) {
        val indexDocument = message as IndexCommand
        val document = DocumentWithUrl(indexDocument.url, indexDocument.document)
        val cleaned = cleaner.compute(document)
        val tokenized = tokenizer.compute(cleaned)
        val vectorized = vectorizer.compute(tokenized)
        publisher.publish("indexed-document-event", IndexedEvent(indexerId, vectorized), MessageType.BROADCAST, IndexedEvent::class.java)
    }
}