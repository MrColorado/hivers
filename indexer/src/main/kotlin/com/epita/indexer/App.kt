package com.epita.indexer

import com.epita.brokerclient.client.BrokerClient
import com.epita.domain.tfidf.cleaner.core.CleanerServiceInterface
import com.epita.domain.tfidf.cleaner.impl.HtmlCleaner
import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.tokenizer.impl.BasicTokenizer
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.domain.tfidf.vectorizer.impl.BasicVectorizer
import com.epita.hivers.core.Hivers
import com.epita.indexer.subscribers.IndexerSubscriber
import com.epita.models.communications.BrokerClientInterface
import com.epita.models.communications.MessageType
import com.epita.models.communications.Publisher
import com.epita.models.commands.IndexerInitCommand
import java.util.*

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient("http://localhost:7000/"))
        bean(CleanerServiceInterface::class.java, HtmlCleaner())
        bean(TokenizerServiceInterface::class.java, BasicTokenizer())
        bean(VectorizerServiceInterface::class.java, BasicVectorizer())
    }

    val publisher = Publisher(hivers.instanceOf(BrokerClientInterface::class.java))

    val indexerId = UUID.randomUUID().toString()

    IndexerSubscriber(
        hivers.instanceOf(BrokerClientInterface::class.java),
        "index-document-command",
        publisher,
        hivers.instanceOf(CleanerServiceInterface::class.java),
        hivers.instanceOf(TokenizerServiceInterface::class.java),
        hivers.instanceOf(VectorizerServiceInterface::class.java)
    )

    publisher.publish("indexer-init-command", IndexerInitCommand(indexerId),
        MessageType.BROADCAST, IndexerInitCommand::class.java)
}