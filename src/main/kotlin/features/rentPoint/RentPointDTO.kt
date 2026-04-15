package com.olga.avshister.features.rentPoint

import com.olga.avshister.features.product.ProductDTO
import kotlinx.serialization.Serializable

@Serializable
data class RentPointDTO(
    val id: Long,
    val name: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val workHours: String,
    val availableProducts: List<ProductDTO>,
) {
}