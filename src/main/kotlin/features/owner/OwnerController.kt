package com.olga.avshister.features.owner

import com.olga.avshister.database.table.TokenTable.token
import com.olga.avshister.domain.Product
import com.olga.avshister.domain.RentPoint
import com.olga.avshister.domain.User
import com.olga.avshister.services.OwnerService
import com.olga.avshister.services.UsersService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class OwnerController(private val call: ApplicationCall) {
    suspend fun registerNewUser(token: String, user: User) {
        try {
            UsersService.getUserByToken(token)?.let { loggedUser ->
                if (loggedUser.role == User.Role.OWNER) {
                    UsersService.registerNewUser(user)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Регистрировать персонал может только владелец")
                }
            } ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Необходима авторизация"
                )
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }

    suspend fun registerProduct(token: String, rentPointId: Long, product: Product) {
        try {
            UsersService.getUserByToken(token)?.let { loggedUser ->
                if (loggedUser.role == User.Role.OWNER) {
                    OwnerService.registerProduct(rentPointId, product)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Регистрировать новые товары может только владелец")
                }
            } ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Необходима авторизация"
                )
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }

    suspend fun deleteRentPoint(token: String, rentPointId: Long) {
        try {
            UsersService.getUserByToken(token)?.let { loggedUser ->
                if (loggedUser.role == User.Role.OWNER) {
                    OwnerService.deleteRentPoint(rentPointId)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Удалять точки аренды может только владелец")
                }
            } ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Необходима авторизация"
                )
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }

    suspend fun registerRentPoint(token: String, rentPoint: RentPoint) {
        try {
            UsersService.getUserByToken(token)?.let { loggedUser ->
                if (loggedUser.role == User.Role.OWNER) {

                    OwnerService.registerRentPoint(rentPoint)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Создавать новые точки аренды может только владелец")
                }
            } ?: run {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Необходима авторизация"
                )
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }
}