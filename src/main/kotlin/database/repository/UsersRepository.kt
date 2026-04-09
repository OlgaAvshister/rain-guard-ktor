package com.olga.avshister.database.repository

import com.olga.avshister.database.table.UserTable
import com.olga.avshister.domain.User

object UsersRepository {
    fun registerNewUser(user: User): User {
        return UserTable.insert(user)
    }

    fun getUser(phone: String): User? {
        return UserTable.fetchUser(phone)
    }
}