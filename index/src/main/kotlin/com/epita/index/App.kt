package com.epita.index

import com.epita.brokerclient.client.BrokerClient
import com.epita.domain.tfidf.tokenizer.core.TokenizerServiceInterface
import com.epita.domain.tfidf.tokenizer.impl.BasicTokenizer
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface
import com.epita.domain.tfidf.vectorizer.impl.BasicVectorizer
import com.epita.hivers.core.Hivers
import com.epita.index.controllers.IndexController
import com.epita.index.controllers.IndexControllerInterface
import com.epita.index.core.IndexServiceInterface
import com.epita.index.impl.RetroIndex
import com.epita.index.subscribers.IndexerSubscriber
import com.epita.models.Constants
import com.epita.models.communications.BrokerClientInterface
import io.javalin.Javalin

fun main() {
    val hivers = Hivers {
        bean(BrokerClientInterface::class.java, BrokerClient(Constants.serverUrl))
        bean(TokenizerServiceInterface::class.java, BasicTokenizer())
        bean(VectorizerServiceInterface::class.java, BasicVectorizer())
        bean(IndexServiceInterface::class.java,
            RetroIndex(instanceOf(TokenizerServiceInterface::class.java),
                instanceOf(VectorizerServiceInterface::class.java)))
        bean(IndexControllerInterface::class.java, IndexController(instanceOf(IndexServiceInterface::class.java)))
    }

    IndexerSubscriber(hivers.instanceOf(BrokerClientInterface::class.java), "indexed-document-event",
        hivers.instanceOf(IndexServiceInterface::class.java))

    val app = Javalin.create { config ->
        config.requestCacheSize = Constants.maxBodySize
    }

    val indexController = hivers.instanceOf(IndexControllerInterface::class.java)

    app.start(Constants.indexPort)
        .get("") { context -> context.html("Hello!") }
        .get("/search", indexController.search)

}