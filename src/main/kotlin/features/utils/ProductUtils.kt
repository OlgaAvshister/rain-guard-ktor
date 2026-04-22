package com.olga.avshister.features.utils

import com.olga.avshister.domain.Product
import com.olga.avshister.domain.Product.Companion.withGeneratedArticul

object ProductUtils {
    fun generateProduct(): Product {
        return when (generateProductType()) {
            Product.ProductType.UMBRELLA -> {
                generateUmbrella()
            }
            Product.ProductType.RAINCOAT -> {
                generateRaincoat()
            }
        }
    }

    private fun generateProductType(): Product.ProductType {
        return Product.ProductType.values().random()
    }

    private fun generateUmbrella(): Product {
        val generatedColor = Product.Colors.values().random()
        return Product(
            productType = Product.ProductType.UMBRELLA,
            printType = Product.PrintType.values().random(),
            color = generatedColor,
            formFactor = listOf(
                Product.FormFactor.FOLDING,
                Product.FormFactor.STICK,
            ).random(),
            size = null,
            condition = Product.ProductCondition.values().random()
        ).withGeneratedArticul()
    }

    private fun generateRaincoat(): Product {
        val generatedColor = listOf(Product.Colors.YELLOW, Product.Colors.RED).random()
        return Product(
            productType = Product.ProductType.RAINCOAT,
            printType = Product.PrintType.values().random(),
            color = generatedColor,
            formFactor = listOf(
                Product.FormFactor.JACKET,
                Product.FormFactor.RAINCOAT,
            ).random(),
            size = Product.Size.values().random(),
            condition = Product.ProductCondition.values().random()
        ).withGeneratedArticul()
    }
}