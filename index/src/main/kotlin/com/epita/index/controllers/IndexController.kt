package com.epita.index.controllers

import com.epita.index.core.IndexServiceInterface
import io.javalin.http.Context

class IndexController(private val service: IndexServiceInterface) : IndexControllerInterface {

    override val search: (Context) -> Unit = {
        val query = it.queryParam("query")
        if (null == query) {
            it.status(403)
            it.html("No query param")
        }
        else {
            val results = service.query(query)
            it.json(results)
        }
    }
}