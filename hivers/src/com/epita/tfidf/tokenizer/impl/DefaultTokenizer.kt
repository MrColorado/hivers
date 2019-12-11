package com.epita.tfidf.tokenizer.impl

import com.epita.tfidf.models.Cleaned
import com.epita.tfidf.models.Tokenized
import com.epita.tfidf.tokenizer.core.Tokenizer

class DefaultTokenizer() : Tokenizer {
    private val stopWords = listOf("i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers", "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves", "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "having", "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because", "as", "until", "while", "of", "at", "by", "for", "with", "about", "against", "between", "into", "through", "during", "before", "after", "above", "below", "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again", "further", "then", "once", "here", "there", "when", "where", "why", "how", "all", "any", "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "should", "now").toList()
    private val synonyms = hashMapOf("amazing" to listOf("incredible",  "unbelievable",  "improbable",  "fabulous",  "wonderful",  "fantastic",  "astonishing",  "astounding",  "extraordinary"),
            "anger" to listOf("enrage",  "infuriate",  "arouse",  "nettle",  "exasperate",  "inflame",  "madden"),
            "angry" to listOf("mad",  "furious",  "enraged",  "excited",  "wrathful",  "indignant",  "exasperated",  "aroused",  "inflamed"),
            "answer" to listOf("reply",  "respond",  "retort",  "acknowledge"))

    override fun compute(document: Cleaned): Tokenized {
        var tokens = slice(document.text)
        tokens = removeStopWord(tokens)
        tokens = stemming(tokens)
        tokens = synonymReplacement(tokens)
        return Tokenized(document.url, tokens)
    }

    private fun slice(text: String): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun removeStopWord(tokens: List<String>): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun stemming(tokens: List<String>): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun synonymReplacement(tokens: List<String>): List<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}