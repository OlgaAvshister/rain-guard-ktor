package com.olga.avshister.domain

import com.olga.avshister.features.card.CardDTO

data class Card(
    val number: String,
    val expired: String,
    val cvv: Int,
) {
    companion object {
        fun Card.toDTO(): CardDTO {
            return CardDTO(
                number = number,
                expired = expired,
                cvv = cvv,
            )
        }
    }
}