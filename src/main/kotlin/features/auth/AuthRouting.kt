package com.olga.avshister.features.auth

import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureAuthRouting() {
    routing {
        post("/auth") {
            val receive = call.receive(AuthReceiveRemote::class)
            val authController = AuthController(call)
            authController.authUser(receive.phone, receive.code)
        }
    }
}