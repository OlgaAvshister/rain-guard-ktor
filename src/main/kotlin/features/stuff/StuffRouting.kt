package com.olga.avshister.features.stuff

import com.olga.avshister.Headers
import io.ktor.server.application.Application
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.routing.put
import io.ktor.server.routing.routing

fun Application.configureStuffRouting() {
    routing {
        put("/condition") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            call.receive(ConditionRemote::class).let {
                StuffController(call).updateCondition (token ?: "", it.productId, it.condition)
            }
        }
    }
}