package com.epita.models.tfidf

data class DocumentCosine(override val url: String, val cosine: Double) : Document