package com.olga.avshister.features.product

import com.olga.avshister.domain.Product.Colors
import com.olga.avshister.domain.Product.FormFactor
import com.olga.avshister.domain.Product.PrintType
import com.olga.avshister.domain.Product.ProductCondition
import com.olga.avshister.domain.Product.ProductType
import com.olga.avshister.domain.Product.Size
import kotlinx.serialization.Serializable

@Serializable
data class ProductDTO(
    val id: Long,
    val productType: ProductType,
    val article: Long = -1,
    val image: String,
    val printType: PrintType,
    val color: Colors,
    val formFactor: FormFactor,
    val size: Size?,
    val condition: ProductCondition
)