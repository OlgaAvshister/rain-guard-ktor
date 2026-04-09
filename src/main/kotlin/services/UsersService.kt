package com.olga.avshister.services

import com.olga.avshister.database.repository.UsersRepository
import com.olga.avshister.domain.User

object UsersService {
    fun getUser(phone: String): User? {
        return UsersRepository.getUser(phone)
    }

    fun getUserByToken(token: String): User? {
        return UsersRepository.getUserByToken(token)
    }

    fun registerNewUser(user: User): User {
        return UsersRepository.registerNewUser(user)
    }
}