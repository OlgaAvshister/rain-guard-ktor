package com.olga.avshister.database.table

import com.olga.avshister.domain.User
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object UserTable : LongIdTable("users") {
    private val phone = varchar("phone", 25)
    private val name = varchar("name", 25)
    private val role = varchar("role", 25)

    fun insert(user: User): User {
        return transaction {
            val newId = UserTable.insertAndGetId {
                it[phone] = user.phone
                it[name] = user.name ?: ""
                it[role] = user.role.name
            }
            user.copy(id = newId.value)
        }
    }

    fun fetchUser(phone: String): User? {
        return transaction {
            UserTable
                .selectAll()
                .where { UserTable.phone eq phone }
                .singleOrNull()
                ?.let {
                    User(
                        id = it[UserTable.id].value,
                        phone = it[UserTable.phone],
                        name = it[name],
                        role = User.Role.entries.find { roleValue -> roleValue.name == it[role] } ?: User.Role.CUSTOMER
                    )
                }
        }
    }
}