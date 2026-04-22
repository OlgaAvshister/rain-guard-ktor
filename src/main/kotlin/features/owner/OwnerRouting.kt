package com.olga.avshister.features.owner

import com.olga.avshister.Headers
import com.olga.avshister.features.product.ProductDTO.Companion.toDomain
import com.olga.avshister.features.product.RegisterProductReceiveRemote
import com.olga.avshister.features.user.UserDTO
import com.olga.avshister.features.user.UserDTO.Companion.toDomain
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.configureOwnerRouting() {
    routing {
        post("/registerStuff") {
            val token = call.request.header(Headers.HEADER_TOKEN)
            val user = call.receive(UserDTO::class).toDomain()
            OwnerController(call).registerNewUser(token ?: "", user)
        }
        post("/registerProduct") {
            try {
                val token = call.request.header(Headers.HEADER_TOKEN)
                call.receive(RegisterProductReceiveRemote::class).let {
                    OwnerController(call).registerProduct(
                        token = token ?: "",
                        rentPointId = it.rentPointId,
                        product = it.product.toDomain())
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
            }
        }
        delete("/deleteRentPoint") {
            try {
                val token = call.request.header(Headers.HEADER_TOKEN)
                val rentPointId = call.queryParameters["rentPointId"]?.toLongOrNull()
                rentPointId?.let {
                    OwnerController(call).deleteRentPoint(token = token ?: "", rentPointId = it)
                    call.respond(HttpStatusCode.OK)
                } ?: call.respond(HttpStatusCode.BadRequest, message = "Не передан id точки аренды")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, message = "message: ${e.message}, cause: ${e.cause}")
            }
        }
    }
}