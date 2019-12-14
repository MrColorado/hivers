package com.epita.domain.tfidf.test

import com.epita.domain.tfidf.tokenizer.impl.BasicTokenizer
import com.epita.models.tfidf.Cleaned
import org.junit.Test

class TokenizerTest {
    @Test
    fun basicExecute() {
        val tokenizer = BasicTokenizer()
        val cleaned = Cleaned("url", "the blue rabbit is fishing in a blue river")
        val tokenized = tokenizer.compute(cleaned)
        assert(tokenized.tokens == listOf("blue", "rabbit", "fish", "blue", "river"))
    }
}