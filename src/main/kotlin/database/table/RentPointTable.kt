package com.olga.avshister.database.table

import com.olga.avshister.database.table.ProductTable.article
import com.olga.avshister.database.table.ProductTable.color
import com.olga.avshister.database.table.ProductTable.condition
import com.olga.avshister.database.table.ProductTable.formFactor
import com.olga.avshister.database.table.ProductTable.printType
import com.olga.avshister.database.table.ProductTable.productType
import com.olga.avshister.database.table.ProductTable.size
import com.olga.avshister.domain.Product
import com.olga.avshister.domain.RentPoint
import com.olga.avshister.features.utils.RentPointUtils
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

object RentPointTable: LongIdTable("rent_points") {
    private val name = varchar("name", 255)
    private val address = varchar("address", 255)
    private val latitude = double("latitude")
    private val longitude = double("longitude")
    private val workHours = varchar("work_hours", 50)

    fun getRentPoints(): List<RentPoint> {
        return transaction {
            val allRentPointRows = RentPointTable
                .selectAll()
                .toList()

            val rentPointsWithoutProducts = arrayListOf<RentPoint>()

            allRentPointRows.forEach {
                rentPointsWithoutProducts.add(
                    RentPoint(
                        id = it[RentPointTable.id].value,
                        name = it[name],
                        address = it[address],
                        latitude = it[latitude],
                        longitude = it[longitude],
                        workHours = it[workHours],
                        availableProducts = emptyList(),
                    )
                )
            }

            val productsByRentPointId = ProductTable
                .selectAll()
                .toList()
                .map {
                    Product(
                        id = it[ProductTable.id].value,
                        rentPointId = it[ProductTable.rentPointId].value,
                        productType = it[productType],
                        article = it[article],
                        printType = it[printType],
                        color = it[color],
                        formFactor = it[formFactor],
                        size = it[size],
                        condition = it[condition]
                    )
                }

            val productsByRentPointIdNew = productsByRentPointId.groupBy { it.rentPointId }

            rentPointsWithoutProducts.map { rentPoint ->
                rentPoint.copy(
                    availableProducts = productsByRentPointIdNew[rentPoint.id] ?: emptyList()
                )
            }
        }
    }

    // Используется только для генерации начального датасета
    fun generateRentPoint(itemsNumber: Int) {
        val generatedRentPoint = RentPointUtils.generateRentPoint()
        transaction {
            val rentPointId = RentPointTable.insertAndGetId {
                it[name] = generatedRentPoint.name
                it[address] = generatedRentPoint.address
                it[latitude] = generatedRentPoint.latitude
                it[longitude] = generatedRentPoint.longitude
                it[workHours] = generatedRentPoint.workHours
            }

            ProductTable.generateProducts(rentPointId.value, itemsNumber)
        }
    }
}