package com.olga.avshister.services

import com.olga.avshister.domain.Token
import com.olga.avshister.domain.User
import org.slf4j.LoggerFactory

object AuthService {
    private val log = LoggerFactory.getLogger(AuthService::class.java)
    private const val RIGHT_SMS_CODE= "1234"
    private const val AUTH_SERVICE_TAG = "AUTH_SERVICE"

    fun authUser(phone: String, smsCode: String): Pair<Token, User> {
        log.info("$AUTH_SERVICE_TAG/authUser, phone: $phone, smsCode: $smsCode")
        return if (isSmsCodeRight(smsCode)) {
            log.info("$AUTH_SERVICE_TAG/authUser, code is right")
            val user = UsersService.getUser(phone)
            user?.id?.let { id ->
                val token = TokenService.getTokenByUserId(id)
                return Pair(token, user)
            } ?: let {
                val userToRegister = User(
                    phone = phone,
                    name = "",
                    role = User.Role.CUSTOMER
                )
                val registeredUser = UsersService.registerNewUser(userToRegister)
                val token = TokenService.getTokenByUserId(registeredUser.id)
                Pair(token, registeredUser)
            }
        } else {
            log.error("$AUTH_SERVICE_TAG/authUser, code is wrong!")
            throw IllegalArgumentException("Неверный SMS-код")
        }
    }

    private fun isSmsCodeRight(smsCode: String): Boolean {
        return (smsCode == RIGHT_SMS_CODE)
    }
}