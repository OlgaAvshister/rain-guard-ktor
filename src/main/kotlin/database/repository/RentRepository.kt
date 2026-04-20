package com.olga.avshister.database.repository

import com.olga.avshister.database.table.RentTable
import com.olga.avshister.domain.Rent

object RentRepository
{
    fun getActiveRent(uid: Long): Rent? {
        return RentTable.getActiveRent(uid)
    }
}