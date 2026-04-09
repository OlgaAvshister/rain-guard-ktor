package com.olga.avshister.services

import com.olga.avshister.database.repository.TokenRepository
import com.olga.avshister.domain.Token
import org.slf4j.LoggerFactory

object TokenService {
    private val log = LoggerFactory.getLogger(TokenService::class.java)
    fun getTokenByUserId(id: Long): Token {
        log.info("TOKEN_SERVICE_TAG/getTokenByUserId called with id=$id")
        return TokenRepository.getTokenByUserId(id).apply {
            log.info("getTokenByUserId called, got token=${this.token}")
        }
    }
}