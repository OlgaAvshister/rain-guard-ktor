package com.olga.avshister.database.users

data class UserDTO(
    val id: Long = 0, // поле с автоинкрементом, не во всех запросал нужно его указывать
    val phone: String,
    val name: String?,
    val role: Role = Role.CUSTOMER
) {
    enum class Role { CUSTOMER, STUFF, OWNER  }
}