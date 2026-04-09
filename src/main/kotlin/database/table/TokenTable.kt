package com.olga.avshister.database.table

import com.olga.avshister.domain.Token
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import java.util.UUID

object TokenTable : LongIdTable("tokens") {
    val token = varchar("token", 255)
    private val userId = reference("user_id", UserTable)

    fun getTokenById(userId: Long): Token {
        return transaction {
            TokenTable
                .selectAll()
                .where { TokenTable.userId eq userId }
                .singleOrNull()
                ?.let {
                    Token(token = it[token])
                } ?: generateToken(userId)
        }
    }

    private fun generateToken(userId: Long): Token {
        val newToken = generateOpaqueToken()
        val insertStatement = TokenTable.insert {
            it[token] = newToken
            it[this.userId] = EntityID(userId, UserTable)
        }

        return Token(insertStatement[token])
    }

    private fun generateOpaqueToken(): String {
        return UUID.randomUUID().toString()
    }

}