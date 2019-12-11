package com.epita.tfidf.tokenizer.core

import com.epita.tfidf.models.Cleaned
import com.epita.tfidf.models.Tokenized

interface Tokenizer {
    fun compute(document: Cleaned) : Tokenized
}