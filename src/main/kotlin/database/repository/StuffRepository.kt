package com.olga.avshister.database.repository

import com.olga.avshister.database.table.ProductTable
import com.olga.avshister.domain.Product

object StuffRepository {
    fun updateCondition(productId: Long, condition: Product.ProductCondition) {
        ProductTable.updateCondition(productId, condition)
    }
}