package com.olga.avshister.features.auth

import com.olga.avshister.database.users.UsersController
import io.ktor.server.application.Application
import io.ktor.server.request.receive
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureAuthRouting() {
    routing {
        post("/auth") {
            val receive = call.receive(AuthReceiveRemote::class)
            val usersController = UsersController(call)
            usersController.authUser(receive.phone, receive.code)
        }
    }
}