package com.olga.avshister.features.card

import kotlinx.serialization.Serializable

@Serializable
data class AddCardReceiveRemote(
    val card: CardDTO
)

@Serializable
data class GetCardsResponseRemote(
    val cards: List<CardDTO>
)