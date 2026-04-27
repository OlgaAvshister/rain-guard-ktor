package com.olga.avshister.features.rentPoint

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRentPointReceiveRemote(
    val name: String,
    val fullAddress: String,
    val latitude: Double,
    val longitude: Double,
    val workHours: String,
)