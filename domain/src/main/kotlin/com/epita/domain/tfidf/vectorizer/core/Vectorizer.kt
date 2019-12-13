package com.epita.domain.tfidf.vectorizer.core

import com.epita.domain.tfidf.models.Tokenized
import com.epita.domain.tfidf.models.Vectorized

interface Vectorizer {
    fun compute(document: Tokenized) : Vectorized
}