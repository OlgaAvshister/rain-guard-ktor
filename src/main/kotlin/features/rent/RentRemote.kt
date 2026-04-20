package com.olga.avshister.features.rent

import kotlinx.serialization.Serializable

@Serializable
data class ActiveRent(
    val rent: RentDTO?,
)
