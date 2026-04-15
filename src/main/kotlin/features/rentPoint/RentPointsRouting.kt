package com.olga.avshister.features.rentPoint

import com.olga.avshister.Headers
import io.ktor.server.application.Application
import io.ktor.server.request.header
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRentPointsRouting() {
    routing {
        get("/rentPoints") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            RentPointController(call).getRentPoints(token ?: "")
        }
    }
}