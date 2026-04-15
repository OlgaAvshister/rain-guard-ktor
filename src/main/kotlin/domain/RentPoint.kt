package com.olga.avshister.domain

import com.olga.avshister.domain.Product.Companion.toDTO
import com.olga.avshister.features.rentPoint.RentPointDTO

data class RentPoint(
    val id: Long = -1,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val workHours: String,
    val availableProducts: List<Product> = emptyList(),
) {
    companion object {
        fun RentPoint.toDTO(): RentPointDTO {
            return RentPointDTO(
                id = this.id,
                name = this.name,
                address = this.address,
                latitude = this.latitude,
                longitude = this.longitude,
                workHours = this.workHours,
                availableProducts = this.availableProducts.map { it.toDTO() },
            )
        }
        fun List<RentPoint>.toDto(): List<RentPointDTO> {
            return this.map { it.toDTO() }
        }
    }
}