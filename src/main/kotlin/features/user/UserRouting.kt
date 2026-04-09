package com.olga.avshister.features.user

import com.olga.avshister.Headers
import io.ktor.server.application.Application
import io.ktor.server.request.header
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureUserRouting() {
    routing {
        get("/user") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            UserController(call).getUser(token ?: "")
        }
    }
}