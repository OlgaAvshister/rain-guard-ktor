package com.olga.avshister.services

import com.olga.avshister.database.repository.CardRepository
import com.olga.avshister.domain.Card

object CardService {
    fun addCard(uid: Long, card: Card) {
        CardRepository.addCard(uid, card)
    }

    fun getCards(uid: Long): List<Card> {
        return CardRepository.getCards(uid)
    }
}