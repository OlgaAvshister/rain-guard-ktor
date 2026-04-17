package com.olga.avshister.features.card

import com.olga.avshister.Headers
import com.olga.avshister.features.card.CardDTO.Companion.toDomain
import io.ktor.server.application.Application
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureCardsRouting() {
    routing {
        post("/addCard") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            val card = call.receive(AddCardReceiveRemote::class).card.toDomain()
            CardController(call).addCard(token, card)
        }

        get("/getCards") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            CardController(call).getCards(token)
        }
    }
}