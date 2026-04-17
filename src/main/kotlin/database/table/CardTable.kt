package com.olga.avshister.database.table

import com.olga.avshister.domain.Card
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.slf4j.LoggerFactory

object CardTable : LongIdTable("cards") {
    private val log = LoggerFactory.getLogger(CardTable::class.java)

    val uid = reference("user_id", UserTable)
    val number = varchar("number", 25)
    val expired = varchar("expired", 25)
    val cvv = integer("cvv")

    fun addCard(userId: Long, card: Card) {
        transaction {
            CardTable.insert {
                it[CardTable.uid] = userId
                it[number] = card.number
                it[expired] = card.expired
                it[cvv] = card.cvv
            }
        }
    }

    fun getCards(uid: Long): List<Card> {
        return transaction {
            CardTable
                .selectAll()
                .where { CardTable.uid eq uid }
                .map {
                    Card(
                        number = it[number],
                        expired = it[expired],
                        cvv = it[cvv]
                    )
                }
        }
    }
}