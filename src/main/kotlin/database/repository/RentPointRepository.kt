package com.olga.avshister.database.repository

import com.olga.avshister.database.table.RentPointTable
import com.olga.avshister.database.table.RentTable
import com.olga.avshister.domain.Rent
import com.olga.avshister.domain.RentPoint

object RentPointRepository {
    fun getRentPoints(): List<RentPoint> {
        return RentPointTable.getRentPoints()
    }

    fun startRent(uid: Long, rent: Rent) {
        RentTable.startRent(uid, rent)
    }

    fun finishRent(uid: Long, rent: Rent) {
        RentTable.finishRent(uid, rent)
    }
}