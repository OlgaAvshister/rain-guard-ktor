package com.olga.avshister.features.rent

import com.olga.avshister.Headers
import io.ktor.server.application.Application
import io.ktor.server.request.header
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRentRouting() {
    routing {
        get("/activeRent") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            RentController(call).getActiveRent(token ?: "")
        }
    }
}