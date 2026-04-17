package com.olga.avshister.database.repository

import com.olga.avshister.database.table.CardTable
import com.olga.avshister.domain.Card

object CardRepository {
    fun addCard(uid: Long, card: Card) {
        CardTable.addCard(uid, card)
    }

    fun getCards(uid: Long): List<Card> {
        return CardTable.getCards(uid)
    }
}