package com.epita.domain.tfidf.vectorizer.impl

import com.epita.models.tfidf.KeywordData
import com.epita.models.tfidf.Tokenized
import com.epita.models.tfidf.Vectorized
import com.epita.domain.tfidf.vectorizer.core.VectorizerServiceInterface

class BasicVectorizer : VectorizerServiceInterface {
    override fun compute(document: Tokenized): Vectorized {
        val map = HashMap<String, MutableList<Int>>()

        for (i in 0 until document.tokens.size) {
            val token = document.tokens[i]
            map.getOrPut(token) { arrayListOf() }.add(i)
        }

        val tokenSize = document.tokens.size.toDouble()

        val result = map
                .map { (key, value) -> key to KeywordData(value.size / tokenSize , value) }
                .toMap()

        return Vectorized(document.url, result)
    }
}