package com.olga.avshister.features.rentPoint

import com.olga.avshister.domain.Product
import com.olga.avshister.domain.Product.Companion.toDTO
import com.olga.avshister.domain.Rent
import com.olga.avshister.domain.RentPoint.Companion.toDTO
import com.olga.avshister.services.RentPointService
import com.olga.avshister.services.RentService
import com.olga.avshister.services.UsersService
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

    suspend fun startRent(token: String?, rent: Rent) {
        try {
            if (token.isNullOrEmpty()) {
                call.respond(HttpStatusCode.Unauthorized, "Передан пустой токен")
            } else {
                UsersService.getUserByToken(token)?.id?.let { uid ->
                    RentPointService.startRent(uid, rent)
                    call.respond(HttpStatusCode.OK)
                } ?: run {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        "Пользователь с указанным токеном не найден, необходима авторизация"
                    )
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }

    suspend fun finishRent(token: String?, rent: Rent) {
        try {
            if (token.isNullOrEmpty()) {
                call.respond(HttpStatusCode.Unauthorized, "Передан пустой токен")
            } else {
                UsersService.getUserByToken(token)?.id?.let { uid ->
                    RentPointService.finishRent(uid, rent)
                    call.respond(HttpStatusCode.OK)
                } ?: run {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        "Пользователь с указанным токеном не найден, необходима авторизация"
                    )
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }
}