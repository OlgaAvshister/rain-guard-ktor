package com.olga.avshister.services

import com.olga.avshister.database.repository.StuffRepository
import com.olga.avshister.domain.Product

object StuffService {
    fun updateCondition(productId: Long, condition: Product.ProductCondition) {
        StuffRepository.updateCondition(productId, condition)
    }
}