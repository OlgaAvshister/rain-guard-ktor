package com.olga.avshister

import com.olga.avshister.features.auth.configureAuthRouting
import com.olga.avshister.features.card.configureCardsRouting
import com.olga.avshister.features.rentPoint.configureRentPointsRouting
import com.olga.avshister.features.user.configureUserRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
    configureSerialization()
    configureAuthRouting()
    configureUserRouting()
    configureRentPointsRouting()
    configureCardsRouting()
    configureRouting()
}
