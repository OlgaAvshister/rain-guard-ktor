package com.olga.avshister.features.stuff

import com.olga.avshister.domain.Product
import kotlinx.serialization.Serializable

@Serializable
data class ConditionRemote(
    val productId: Long,
    val condition: Product.ProductCondition,
)
