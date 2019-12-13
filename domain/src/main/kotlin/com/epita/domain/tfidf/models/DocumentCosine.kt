package com.epita.domain.tfidf.models

data class DocumentCosine(override val url: String, val cosine: Double) : Document