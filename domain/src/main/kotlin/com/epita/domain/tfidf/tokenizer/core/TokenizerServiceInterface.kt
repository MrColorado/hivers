package com.epita.domain.tfidf.tokenizer.core

import com.epita.models.tfidf.Cleaned
import com.epita.models.tfidf.Tokenized

interface TokenizerServiceInterface {
    fun compute(document: Cleaned) : Tokenized
}