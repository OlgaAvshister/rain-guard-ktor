package com.olga.avshister.services

import com.olga.avshister.database.repository.RentPointRepository
import com.olga.avshister.domain.Rent
import com.olga.avshister.domain.RentPoint

object RentPointService {
    fun getRentPoints(token: String): List<RentPoint> {
        return RentPointRepository.getRentPoints()
    }

    fun startRent(uid: Long, rent: Rent) {
        RentPointRepository.startRent(uid, rent)
    }

    fun finishRent(uid: Long, rent: Rent) {
        RentPointRepository.finishRent(uid, rent)
    }
}