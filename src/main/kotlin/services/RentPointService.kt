package com.olga.avshister.services

import com.olga.avshister.database.repository.RentPointRepository
import com.olga.avshister.domain.RentPoint

object RentPointService {
    fun getRentPoints(token: String): List<RentPoint> {
        return RentPointRepository.getRentPoints()
    }
}