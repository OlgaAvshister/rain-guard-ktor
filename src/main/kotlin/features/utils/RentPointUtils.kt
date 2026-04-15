package com.olga.avshister.features.utils

import com.olga.avshister.domain.RentPoint
import kotlin.random.Random

object RentPointUtils {
    fun generateRentPoint(): RentPoint {
        val rentPointGeneratedNumber = Random.nextInt(1, 100)
        val generatedLatitude = Random.nextDouble(55.75, 55.76)
        val generatedLongitude = Random.nextDouble(37.62, 37.63)
        val workHoursFrom = Random.nextInt(8, 10)
        val workHoursTo = Random.nextInt(18, 22)

        return RentPoint(
            name = "Точка аренды №$rentPointGeneratedNumber",
            address = "Где-то в России находится точка аренды №$rentPointGeneratedNumber",
            latitude = generatedLatitude,
            longitude = generatedLongitude,
            workHours = "с $workHoursFrom до $workHoursTo",
            availableProducts = listOf()
        )
    }
}