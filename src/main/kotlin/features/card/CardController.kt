package com.olga.avshister.features.card

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond
import com.olga.avshister.domain.Card
import com.olga.avshister.domain.Card.Companion.toDTO
import com.olga.avshister.services.CardService
import com.olga.avshister.services.UsersService

class CardController(private val call: ApplicationCall) {
    suspend fun addCard(token: String?, card: Card) {
        try {
            if (token.isNullOrEmpty()) {
                call.respond(HttpStatusCode.Unauthorized, "Передан пустой токен")
            } else {
                UsersService.getUserByToken(token)?.id?.let { uid ->
                    CardService.addCard(uid, card)
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

    suspend fun getCards(token: String?) {
        try {
            if (token.isNullOrEmpty()) {
                call.respond(HttpStatusCode.Unauthorized, "Передан пустой токен")
            } else {
                UsersService.getUserByToken(token)?.id?.let { uid ->
                    val cards = CardService.getCards(uid)
                    call.respond(HttpStatusCode.OK, cards.map { it.toDTO() })
                } ?: run {
                    call.respond(HttpStatusCode.Unauthorized, "Пользователь не найден")
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
        }
    }
}