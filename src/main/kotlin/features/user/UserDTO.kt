package com.olga.avshister.features.user

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Long = 0, // поле с автоинкрементом, не во всех запросал нужно его указывать
    val phone: String,
    val name: String?,
    val role: String?
)