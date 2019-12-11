package com.epita.tfidf.cleaner.core

import com.epita.tfidf.models.Cleaned

interface Cleaner {
    fun compute(document: String) : Cleaned
}