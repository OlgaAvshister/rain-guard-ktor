package com.olga.avshister.features.user

import com.olga.avshister.domain.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id: Long? = 0, // поле с автоинкрементом, не во всех запросил нужно его указывать
    val phone: String,
    val name: String?,
    val role: String?
) {
    companion object {
        fun UserDTO.toDomain(): User {
            return User(
                phone = phone,
                name = name,
                role = User.Role.entries.find { it.name == role } ?: User.Role.CUSTOMER
            )
        }
    }
}