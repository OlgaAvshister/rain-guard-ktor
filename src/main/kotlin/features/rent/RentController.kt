package com.olga.avshister.features.rent

import com.olga.avshister.domain.Rent.Companion.toDTO
import com.olga.avshister.services.RentService
import com.olga.avshister.services.UsersService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class RentController(private val call: ApplicationCall) {
    suspend fun getActiveRent(token: String) {
        try {
            val user = UsersService.getUserByToken(token)
            user?.let { user ->
                val rent = RentService.getActiveRent(user.id)
                rent?.let { activeRent ->
                    call.respond(
                        HttpStatusCode.OK,
                        ActiveRent(activeRent.toDTO())
                    )
                } ?: run {
                    call.respond(HttpStatusCode.OK, ActiveRent(null))
                }

            } ?: call.respond(HttpStatusCode.NotFound, "Пользователь не найден")

        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }

    suspend fun getCompletedRents(token: String, rentPointId: Long?) {
        try {
            val user = UsersService.getUserByToken(token)
            user?.let { _ ->
                rentPointId?.let { pointId ->
                    val rents = RentService
                        .getCompletedRents(pointId)

                    val rentsDTO =
                        rents
                            .map { it.toDTO() }
                    call.respond(HttpStatusCode.OK, rentsDTO)
                } ?: call.respond(HttpStatusCode.BadRequest, message = "Не указан id пункта возврата")
            } ?: call.respond(HttpStatusCode.Unauthorized, "Пользователь не авторизован")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }
}