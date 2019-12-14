package com.epita.domain.tfidf.vectorizer.core

import com.epita.models.tfidf.Tokenized
import com.epita.models.tfidf.Vectorized

interface Vectorizer {
    fun compute(document: Tokenized) : Vectorized
}