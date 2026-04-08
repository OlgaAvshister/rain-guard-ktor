package com.olga.avshister.database.users

import com.olga.avshister.database.users.UserDTO.Role
import com.olga.avshister.features.auth.AuthResponseRemote
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

class UsersController(private val call: ApplicationCall) {
    suspend fun authUser(phone: String, smsCode: String) {
        if (isSmsCodeRight(smsCode)) {
                Users.fetchUser(phone)?.let { fetchedUser ->
                    call.respond(
                        AuthResponseRemote(
                            id = fetchedUser.id,
                            phone = fetchedUser.phone,
                            name = fetchedUser.name,
                            role = fetchedUser.role.name
                        )
                    )
                } ?: run {
                    val userToRegisterDTO = UserDTO(
                        phone = phone,
                        name = "",
                        role = Role.CUSTOMER
                    )

                    val registeredUser = registerNewUser(userToRegisterDTO)
                    call.respond(
                        AuthResponseRemote(
                            id = registeredUser.id,
                            phone = registeredUser.phone,
                            name = registeredUser.name,
                            role = registeredUser.role.name
                        )
                    )
                }
        } else {
            call.respond(status = HttpStatusCode.BadRequest, message = "Неверный SMS-код")
        }
    }

    private fun isSmsCodeRight(smsCode: String): Boolean {
        return (smsCode == RIGHT_SMS_CODE)
    }

    private fun registerNewUser(user: UserDTO): UserDTO {
        return Users.insert(user)
    }

    companion object {
        const val RIGHT_SMS_CODE= "1234"
    }
}