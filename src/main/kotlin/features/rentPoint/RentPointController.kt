package com.olga.avshister.features.rentPoint

import com.olga.avshister.domain.Product
import com.olga.avshister.domain.Product.Companion.toDTO
import com.olga.avshister.domain.RentPoint.Companion.toDTO
import com.olga.avshister.services.RentPointService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class RentPointController(private val call: ApplicationCall) {
    suspend fun getRentPoints(token: String) {
        try {
            val rentPoints = RentPointService.getRentPoints(token)
            if (rentPoints.isEmpty()) {
                call.respond(HttpStatusCode.NotFound, "Ошибка загрузки точек аренды/возврата")
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    rentPoints.map { it.toDTO() }
                )
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }
}