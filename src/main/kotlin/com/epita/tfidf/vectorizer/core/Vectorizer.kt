package com.epita.tfidf.vectorizer.core

import com.epita.tfidf.models.Tokenized
import com.epita.tfidf.models.Vectorized

interface Vectorizer {
    fun compute(document: Tokenized) : Vectorized
}