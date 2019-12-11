package com.epita.tfidf.cleaner.impl

import com.epita.tfidf.cleaner.core.Cleaner
import com.epita.tfidf.models.Cleaned
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode

class HtmlCleaner : Cleaner {

    private fun getTextFromNode(node: Node) : String {
        val builder = StringBuilder(" ")
        for (child in node.childNodes()) {
            if (child is TextNode) {
                builder.append(child.text())
            } else if (child is Element) {
                builder.append(getTextFromNode(child))
            }
        }
        return builder.toString()
    }

    override fun compute(document: Document): Cleaned {
        val builder = StringBuilder()
        document.allElements.forEach { e -> builder.append(getTextFromNode(e)) }
        return Cleaned(document.baseUri(), builder.toString())
    }
}