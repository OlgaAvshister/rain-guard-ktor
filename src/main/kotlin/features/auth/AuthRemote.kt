package com.olga.avshister.features.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthReceiveRemote(
    val phone: String,
    val code: String,
)

@Serializable
data class AuthResponseRemote(
    val id: Long,
    val phone: String,
    val name: String?,
    val role: String
)