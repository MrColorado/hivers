package com.epita.domain.tfidf.cleaner.core

import com.epita.models.tfidf.Cleaned
import com.epita.models.tfidf.DocumentWithUrl

interface CleanerServiceInterface {
    fun compute(document: DocumentWithUrl) : Cleaned
}