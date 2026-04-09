package com.olga.avshister.features.auth

import com.olga.avshister.features.user.UserDTO
import com.olga.avshister.features.token.TokenDTO
import kotlinx.serialization.Serializable

@Serializable
data class AuthReceiveRemote(
    val phone: String,
    val code: String,
)

@Serializable
data class AuthResponseRemote(
    val token: TokenDTO,
    val user: UserDTO,
)