package com.epita.tfidf.test

import com.epita.tfidf.models.Cleaned
import com.epita.tfidf.tokenizer.impl.BasicTokenizer
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