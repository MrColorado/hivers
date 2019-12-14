package com.epita.index.controllers

import io.javalin.http.Context

interface IndexControllerInterface {
    val search: (Context) -> Unit
}