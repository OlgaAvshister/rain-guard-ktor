package com.olga.avshister.features.user

import com.olga.avshister.domain.User.Companion.toUserDTO
import com.olga.avshister.services.UsersService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class UserController(private val call: ApplicationCall) {
    suspend fun getUser(token: String) {
        try {
            val user = UsersService.getUserByToken(token)
            user?.let { user ->
                call.respond(
                    HttpStatusCode.OK,
                    user.toUserDTO()
                )
            } ?: call.respond(HttpStatusCode.NotFound, "Пользователь не найден")

        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }
}