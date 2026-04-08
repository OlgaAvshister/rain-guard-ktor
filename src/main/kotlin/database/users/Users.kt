package com.olga.avshister.database.users

import com.olga.avshister.database.users.UserDTO.Role
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object Users : LongIdTable("users") {
    private val phone = varchar("phone", 25)
    private val name = varchar("name", 25)
    private val role = varchar("role", 25)

    fun insert(userDTO: UserDTO): UserDTO {
        return transaction {
            val newId = Users.insertAndGetId {
                it[phone] = userDTO.phone
                it[name] = userDTO.name ?: ""
                it[role] = userDTO.role.name
            }
            userDTO.copy(id = newId.value)
        }
    }

    fun fetchUser(phone: String): UserDTO? {
        return transaction {
                Users
                .selectAll()
                .where { Users.phone eq phone }
                .singleOrNull()
                ?.let {
                    UserDTO(
                        id = it[Users.id].value,
                        phone = it[Users.phone],
                        name = it[name],
                        role = Role.entries.find { roleValue -> roleValue.name == it[role] } ?: Role.CUSTOMER
                    )
                }
        }
    }
}