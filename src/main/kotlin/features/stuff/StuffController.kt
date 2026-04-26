package com.olga.avshister.features.stuff

import com.olga.avshister.domain.Product
import com.olga.avshister.domain.User
import com.olga.avshister.services.StuffService
import com.olga.avshister.services.UsersService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class StuffController(private val call: ApplicationCall) {
    suspend fun updateCondition(token: String, productId: Long, condition: Product.ProductCondition) {
        try {
            UsersService.getUserByToken(token)?.let { loggedUser ->
                if (loggedUser.role == User.Role.STUFF) {
                    StuffService.updateCondition(productId, condition)
                    call.respond(HttpStatusCode.OK)
                } else {
                    call.respond(HttpStatusCode.Forbidden, "Обновлять состояние товара может только персонал")
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