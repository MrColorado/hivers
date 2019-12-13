package com.epita.domain.tfidf.tokenizer.core

import com.epita.domain.tfidf.models.Cleaned
import com.epita.domain.tfidf.models.Tokenized

interface Tokenizer {
    fun compute(document: Cleaned) : Tokenized
}