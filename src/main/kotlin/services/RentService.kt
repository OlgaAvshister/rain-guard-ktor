package com.olga.avshister.services

import com.olga.avshister.database.repository.RentRepository
import com.olga.avshister.domain.Rent

object RentService {
    fun getActiveRent(uid: Long): Rent? {
        return RentRepository.getActiveRent(uid)
    }

    fun getCompletedRents(rentPointId: Long): List<Rent> {
        return RentRepository.getCompletedRents(rentPointId)
    }
}