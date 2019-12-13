package com.epita.domain.tfidf.cleaner.subscribers

import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.models.DocumentWithUrl
import com.epita.models.BrokerClientInterface
import com.epita.models.MessageType
import com.epita.models.Publisher
import com.epita.models.Subscriber
import com.epita.models.commands.TokenizerCommand
import com.epita.models.events.CrawledEvent

class CleanerSubscriber : Subscriber {

    private val cleanerService: CleanerServiceInterface
    private val publisher: Publisher

    constructor(brokerClient: BrokerClientInterface, topic: String, publisher: Publisher, cleanerService: CleanerServiceInterface) :
            super(brokerClient, topic) {
        init()
        this.publisher = publisher
        this.cleanerService = cleanerService
    }
    override fun <CLASS> handle(message: CLASS) {
        val crawledEvent = message as CrawledEvent
        val document = DocumentWithUrl(crawledEvent.url, crawledEvent.document)
        val cleanedDocument = cleanerService.compute(document)
        publisher.publish("tokenizer-command", TokenizerCommand(cleanedDocument.url, cleanedDocument.text),
            MessageType.ONCE, TokenizerCommand::class.java)
    }
}