package com.olga.avshister.services

import com.olga.avshister.database.repository.OwnerRepository
import com.olga.avshister.domain.Product
import com.olga.avshister.domain.RentPoint

object OwnerService {
    suspend fun registerProduct(rentPointId: Long, product: Product) {
        OwnerRepository.registerProduct(rentPointId, product)
    }

    suspend fun deleteRentPoint(rentPointId: Long) {
        OwnerRepository.deleteRentPoint(rentPointId)
    }

    suspend fun registerRentPoint(rentPoint: RentPoint) {
        OwnerRepository.registerRentPoint(rentPoint)
    }
}