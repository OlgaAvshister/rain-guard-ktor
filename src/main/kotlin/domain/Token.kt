package com.olga.avshister.domain

import com.olga.avshister.features.token.TokenDTO

data class Token(val token: String) {
    companion object {
        fun Token.toTokenDTO(): TokenDTO {
            return TokenDTO(
                token = token
            )
        }
    }
}