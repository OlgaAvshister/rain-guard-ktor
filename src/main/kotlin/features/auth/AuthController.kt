package com.olga.avshister.features.auth

import com.olga.avshister.Headers
import com.olga.avshister.domain.Token.Companion.toTokenDTO
import com.olga.avshister.domain.User.Companion.toUserDTO
import com.olga.avshister.services.AuthService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class AuthController(private val call: ApplicationCall) {
    suspend fun authUser(phone: String, smsCode: String) {
        try {
            val (token, user) = AuthService.authUser(phone, smsCode)
            call.response.headers.append(Headers.HEADER_TOKEN, token.token)
            call.respond(
                HttpStatusCode.OK,
                AuthResponseRemote(
                    token = token.toTokenDTO(),
                    user = user.toUserDTO(),
                )
            )
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }
}