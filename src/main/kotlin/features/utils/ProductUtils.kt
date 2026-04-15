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

    private fun getImageResource(productType: Product.ProductType, colors: Product.Colors): String {
        val image: String = when (productType) {
            Product.ProductType.UMBRELLA -> {
                when (colors) {
                    Product.Colors.RED -> {
                        "ic_umbrella_red"
                    }

                    Product.Colors.YELLOW -> {
                        "ic_umbrella_yellow"
                    }

                    Product.Colors.WHITE -> {
                        "ic_umbrella_white"
                    }

                    Product.Colors.GREEN -> {
                        "ic_umbrella_green"
                    }

                    Product.Colors.BLACK -> {
                        "ic_umbrella_black"
                    }

                    Product.Colors.PURPLE -> {
                        "ic_umbrella_purple"
                    }
                }
            }

            Product.ProductType.RAINCOAT -> {
                when (colors) {
                    Product.Colors.RED -> {
                        "ic_raincoat_red"
                    }
                    Product.Colors.YELLOW -> {
                        "ic_raincoat_yellow"
                    }
                    else -> {
                        throw IllegalArgumentException("Недопустимый цвет для дождевика")
                    }
                }
            }
        }
        return image
    }

    private fun generateUmbrella(): Product {
        val generatedColor = Product.Colors.values().random()
        return Product(
            productType = Product.ProductType.UMBRELLA,
            image = getImageResource(productType = Product.ProductType.UMBRELLA, colors = generatedColor),
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
            image = getImageResource(productType = Product.ProductType.RAINCOAT, colors = generatedColor),
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