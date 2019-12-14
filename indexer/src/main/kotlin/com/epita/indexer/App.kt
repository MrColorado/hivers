package com.epita.indexer

import com.epita.brokerclient.client.BrokerClient
import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.cleaner.impl.HtmlCleaner
import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.tokenizer.impl.BasicTokenizer
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.domain.tfidf.vectorizer.impl.BasicVectorizer
import com.epita.hivers.core.Hivers
import com.epita.indexer.subscribers.IndexDocumentCommandSubscriber
import com.epita.models.Constants
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.Publisher
import com.epita.models.commands.IndexerInitCommand
import com.epita.models.communications.PublisherInterface
import java.util.*

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient(Constants.serverUrl))
        bean(CleanerServiceInterface::class.java, HtmlCleaner())
        bean(TokenizerServiceInterface::class.java, BasicTokenizer())
        bean(VectorizerServiceInterface::class.java, BasicVectorizer())
        bean(PublisherInterface::class.java, Publisher(instanceOf(BrokerClientInterface::class.java)))
    }

    val publisher = hivers.instanceOf(PublisherInterface::class.java)

    val indexerId = UUID.randomUUID().toString()

    IndexDocumentCommandSubscriber(
        hivers.instanceOf(BrokerClientInterface::class.java),
        "index-document-command",
        publisher,
        hivers.instanceOf(CleanerServiceInterface::class.java),
        hivers.instanceOf(TokenizerServiceInterface::class.java),
        hivers.instanceOf(VectorizerServiceInterface::class.java),
        indexerId
    )

    publisher.publish("indexer-init-command", IndexerInitCommand(indexerId),
        MessageType.BROADCAST, IndexerInitCommand::class.java)
}