package com.epita.indexer.subscribers

import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.Publisher
import com.epita.models.communications.Subscriber
import com.epita.models.events.IndexedEvent
import com.epita.models.tfidf.DocumentWithUrl

class IndexerSubscriber : Subscriber {

    private val publisher: Publisher
    private val cleaner: CleanerServiceInterface
    private val tokenizer: TokenizerServiceInterface
    private val vectorizer: VectorizerServiceInterface

    constructor(brokerClient: BrokerClientInterface, topic: String, publisher: Publisher,
                cleaner: CleanerServiceInterface, tokenizer: TokenizerServiceInterface,
                vectorizer: VectorizerServiceInterface) : super(brokerClient, topic) {
        init()
        this.publisher = publisher
        this.cleaner = cleaner
        this.tokenizer = tokenizer
        this.vectorizer = vectorizer
    }

    override fun <CLASS> handle(message: CLASS) {
        val document = DocumentWithUrl("", "")
        val cleaned = cleaner.compute(document)
        val tokenized = tokenizer.compute(cleaned)
        val vectorized = vectorizer.compute(tokenized)
        publisher.publish("indexed-event", IndexedEvent(vectorized), MessageType.BROADCAST, IndexedEvent::class.java)
    }
}