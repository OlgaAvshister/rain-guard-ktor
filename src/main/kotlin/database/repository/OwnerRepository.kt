package com.olga.avshister.database.repository

import com.olga.avshister.database.table.ProductTable
import com.olga.avshister.database.table.RentPointTable
import com.olga.avshister.domain.Product
import com.olga.avshister.domain.RentPoint

object OwnerRepository {
    suspend fun registerProduct(rentPointId: Long, product: Product) {
        ProductTable.registerProduct(rentPointId, product)
    }

    suspend fun registerRentPoint(rentPoint: RentPoint) {
        RentPointTable.registerRentPoint(rentPoint)
    }

    suspend fun deleteRentPoint(rentPointId: Long) {
        RentPointTable.deleteRenPoint(rentPointId)
    }
}