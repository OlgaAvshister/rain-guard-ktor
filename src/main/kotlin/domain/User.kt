package com.olga.avshister.domain

import com.olga.avshister.features.user.UserDTO

data class User(
    val id: Long = -1, // поле с автоинкрементом, не во всех запросах нужно его указывать
    val phone: String,
    val name: String?,
    val role: Role = Role.CUSTOMER
) {
    enum class Role { CUSTOMER, STUFF, OWNER  }

    companion object {
        fun User.toUserDTO(): UserDTO {
            return UserDTO(
                id = this.id,
                phone = this.phone,
                name = this.name,
                role = this.role.name
            )
        }
    }
}