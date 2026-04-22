package com.olga.avshister.features.product

import kotlinx.serialization.Serializable

@Serializable
data class RegisterProductReceiveRemote(
    val product: ProductDTO,
    val rentPointId: Long,
)