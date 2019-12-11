package com.epita.tfidf.utils

class Stemming {
    companion object {
        fun execute(word: String) : String {
            var updatedString = word.removeSuffix("ing")
            updatedString = updatedString.removeSuffix("ed")
            updatedString = updatedString.removeSuffix("s")
            if (updatedString.endsWith("ies"))
            {
                updatedString.removePrefix("ies")
                updatedString.plus('y')
            }
            return updatedString
        }
    }
}