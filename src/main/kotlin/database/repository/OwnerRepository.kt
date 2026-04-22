package com.olga.avshister.database.repository

import com.olga.avshister.database.table.ProductTable
import com.olga.avshister.domain.Product

object OwnerRepository {
    suspend fun registerProduct(rentPointId: Long, product: Product) {
        ProductTable.registerProduct(rentPointId, product)
    }
}