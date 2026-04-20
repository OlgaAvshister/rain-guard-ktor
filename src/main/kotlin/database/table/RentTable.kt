package com.olga.avshister.database.table

import com.olga.avshister.domain.Rent
import org.jetbrains.exposed.v1.core.and
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.core.isNull
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import org.slf4j.LoggerFactory

object RentTable: LongIdTable("rents") {
    private val log = LoggerFactory.getLogger(RentTable::class.java)

    val uid = reference("user_id", UserTable)
    val startedAt = long("started_at")
    val finishedAt = long("finished_at").nullable()
    val startRentPointId = long("start_point_id")
    val finishRentPointId = long("finish_point_id").nullable()
    val productIds = array<Long>("product_ids")
    val cardNumber = varchar("card_number", 25)
    val rate = enumerationByName<Rent.Rate>("rate", 25)

    fun startRent(uid: Long, rent: Rent) {
        transaction {
            RentTable.insert {
                it[RentTable.uid] = uid
                it[startedAt] = rent.startedAt
                it[startRentPointId] = rent.startRentPointId
                it[productIds] = rent.productIds
                it[cardNumber] = rent.cardNumber
                it[rate] = rent.rate
            }
        }
    }

    fun getActiveRent(uid: Long): Rent? {
        return transaction {
            RentTable
                .selectAll()
                .where { (RentTable.uid eq uid) and (finishRentPointId.isNull()) }
                .singleOrNull()
                ?.let {
                    Rent(
                        customerId = it[RentTable.uid].value,
                        startedAt = it[startedAt],
                        productIds = it[productIds],
                        startRentPointId = it[startRentPointId],
                        cardNumber = it[cardNumber],
                        rate = it[rate],
                    )
                }
        }
    }

    fun finishRent(uid: Long, rent: Rent) {
        transaction {
            RentTable
                .update({ (RentTable.uid eq uid) and (finishRentPointId.isNull()) }) {
                    it[finishedAt] = rent.finishedAt
                    it[finishRentPointId] = rent.finishRentPointId
                }
            ProductTable
                .update( { ProductTable.id inList rent.productIds } ) {
                    it[rentPointId] = rent.finishRentPointId!!
                }
        }
    }
}