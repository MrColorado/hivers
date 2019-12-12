package com.epita.tfidf.vectorizer.impl

import com.epita.tfidf.models.KeywordData
import com.epita.tfidf.models.Tokenized
import com.epita.tfidf.models.Vectorized
import com.epita.tfidf.vectorizer.core.Vectorizer

class BasicVectorizer : Vectorizer {
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