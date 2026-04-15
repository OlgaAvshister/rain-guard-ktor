package com.olga.avshister.database.repository

import com.olga.avshister.database.table.RentPointTable
import com.olga.avshister.domain.RentPoint

object RentPointRepository {
    fun getRentPoints(): List<RentPoint> {
        return RentPointTable.getRentPoints()
    }
}