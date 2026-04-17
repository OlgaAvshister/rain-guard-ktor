package com.olga.avshister.features.card

import com.olga.avshister.domain.Card
import kotlinx.serialization.Serializable

@Serializable
data class CardDTO(
    val number: String,
    val expired: String, // нужно будет переделать формат 04/26
    val cvv: Int,
) {
    companion object {
        fun CardDTO.toDomain(): Card {
            return Card(
                number = this.number,
                expired = this.expired,
                cvv = this.cvv
            )
        }
    }
}