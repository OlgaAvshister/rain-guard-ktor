package com.olga.avshister.domain

import com.olga.avshister.features.product.ProductDTO
import kotlin.math.absoluteValue

data class Product(
    val id: Long = -1, // инвентарный номер
    val rentPointId: Long = -1,
    val productType: ProductType, // зонт/дождевик
    val article: Long = -1, // поле, которое однозначно закрепляет за собой набор характеристик (несколько товаров с одинаковыми атрибутами должны иметь одинаковый артикул)
    val printType: PrintType, // есть принт/нет принта
    val color: Colors,
    val formFactor: FormFactor, // FOLDING (складывающийся)/STICK (трость) для зонта; JACKET (куртка)/ FULLBODY_RAINCOAT для дождевика на всё тело
    val size: Size?, // только для дождевика
    val condition: ProductCondition? = ProductCondition.READY // состояние товара
) {

    enum class ProductType(val value: String) {
        UMBRELLA("Зонт"), RAINCOAT("Дождевик")
    }

    enum class Colors(val value: String) {
        RED("Красный"), YELLOW("Желтый"), WHITE("Белый"),
        GREEN("Зеленый"), BLACK("Черный"), PURPLE("Фиолетовый")
    }

    enum class PrintType(val value: String) {
        WITH_PRINT("С принтом"),
        WITHOUT_PRINT("Без принта")
    }

    enum class FormFactor(val value: String) {
        JACKET("Куртка"),
        RAINCOAT("На все тело"),
        FOLDING("Складной"),
        STICK("Трость")
    }

    enum class Size(val value: String) {
        XS("XS"), S("S"), M("M"), L(""), XL("XL")
    }

    /**
     * Данный класс используется для записи состояния товара при сдаче клиентом на пункт приема,
     * а также при выводе аналитики для владельца
     */
    enum class ProductCondition(val valueStuff: String, val valueOwner: String) {
        READY(valueStuff = "Все хорошо", valueOwner = "Чистые/готовые"),
        DIRTY(valueStuff = "Нужна стирка", valueOwner = "Грязные"),
        BROKEN(valueStuff = "Нужно заменить", valueOwner = "Неисправные"),
        BOUGHT(valueStuff = "Выкуплен", valueOwner = "Выкуплен"),
        BOOKED(valueStuff = "Арендован", valueOwner = "Арендован"),
    }

    companion object {
        fun Product.toDTO(): ProductDTO {
            return ProductDTO(
                id = this.id,
                productType = ProductType.valueOf(this.productType.name),
                article = this.article,
                printType = PrintType.valueOf(this.printType.name),
                color = this.color,
                size = this.size,
                formFactor = FormFactor.valueOf(this.formFactor.name),
                condition = this.condition ?: ProductCondition.READY
            )
        }

        /**
         * Extension-функция для генерации артикула
         *
         * Все продукты с одинаковыми характеристиками (кроме id)
         * будут иметь одинаковый articul.
         */
        fun Product.generateArticul(): Long {
            val key = buildString {
                append(productType.name)
                append("|")
                append(printType.name)
                append("|")
                append(color.name)
                append("|")
                append(formFactor.name)
                append("|")
                append(size?.name ?: "NO_SIZE")
            }.lowercase()

            return key.hashCode().toLong().absoluteValue
        }

        /**
         * Возвращает копию товара с заполненным артикулом
         */
        fun Product.withGeneratedArticul(): Product {
            return this.copy(
                article = generateArticul()
            )
        }
    }
}