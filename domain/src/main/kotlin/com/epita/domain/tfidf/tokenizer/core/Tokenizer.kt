package com.epita.domain.tfidf.tokenizer.core

import com.epita.domain.tfidf.models.Cleaned
import com.epita.models.tfidf.Tokenized

interface Tokenizer {
    fun compute(document: Cleaned) : Tokenized
}