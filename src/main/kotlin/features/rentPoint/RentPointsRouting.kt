package com.olga.avshister.features.rentPoint

import com.olga.avshister.Headers
import com.olga.avshister.features.rent.RentDTO
import com.olga.avshister.features.rent.RentDTO.Companion.toDomain
import io.ktor.server.application.Application
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureRentPointsRouting() {
    routing {
        get("/rentPoints") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            RentPointController(call).getRentPoints(token ?: "")
        }

        post("/startRent") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            val rent = call.receive(RentDTO::class).toDomain()
            RentPointController(call).startRent(token, rent)
        }

        post("/finishRent") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            val rent = call.receive(RentDTO::class).toDomain()
            RentPointController(call).finishRent(token, rent)
        }
    }
}