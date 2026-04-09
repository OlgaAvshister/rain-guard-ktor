package com.olga.avshister.database.repository

import com.olga.avshister.database.table.TokenTable
import com.olga.avshister.domain.Token

object TokenRepository {
    fun getTokenByUserId(id: Long): Token {
        return TokenTable.getTokenById(id)
    }
}