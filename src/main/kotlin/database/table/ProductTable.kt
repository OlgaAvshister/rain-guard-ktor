package com.olga.avshister.database.table

import com.olga.avshister.domain.Product
import com.olga.avshister.domain.Product.Companion.withGeneratedArticul
import com.olga.avshister.domain.Product.ProductCondition
import com.olga.avshister.domain.Product.ProductType
import com.olga.avshister.features.utils.ProductUtils.generateProduct
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.LongIdTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.core.inList
import org.jetbrains.exposed.v1.jdbc.batchInsert
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import org.slf4j.LoggerFactory

/**
 * Каждый товар нужно создавать для конкретного пункта выдачи,
 * у нас создан constraint для rentPointId, если пункта с rentPointId не существует,
 * будет ошибка при создании новой записи в таблице
 */
object ProductTable: LongIdTable("products") {
    private val log = LoggerFactory.getLogger(ProductTable::class.java)

    val rentPointId = reference("rent_point_id", RentPointTable)
    val productType = enumerationByName<ProductType>("product_type", 50)
    val article = long("article")
    val printType = enumerationByName<Product.PrintType>("print_type", 50)
    val color = enumerationByName<Product.Colors>("color", 50)
    val formFactor = enumerationByName<Product.FormFactor>("form_factor", 50)
    val size = enumerationByName<Product.Size>("size", 50).nullable()
    val condition = enumerationByName<ProductCondition>("condition", 50)

    fun getProductById(id: Long): Product? {
        return transaction {
            ProductTable
                .selectAll()
                .where { ProductTable.id eq id }
                .singleOrNull()
                ?.let {
                    Product(
                        id = it[ProductTable.id].value,
                        productType = it[productType],
                        article = it[article],
                        printType = it[printType],
                        color = it[color],
                        formFactor = it[formFactor],
                        size = it[size],
                        condition = it[condition]
                    )
                }
        }
    }

    fun getProductsByIds(ids: List<Long>): List<Product> {
        return transaction {
            ProductTable
                .selectAll()
                .where { ProductTable.id inList ids }  // Такой синтаксис должен работать
                .map {
                    Product(
                        id = it[ProductTable.id].value,
                        productType = it[productType],
                        article = it[article],
                        printType = it[printType],
                        color = it[color],
                        formFactor = it[formFactor],
                        size = it[size],
                        condition = it[condition]
                    )
                }
        }
    }

    fun registerProduct(rentPointId: Long, product: Product) {
        val rentPointEntityId = EntityID(rentPointId, RentPointTable)
        transaction {
            ProductTable.insert {
                it[ProductTable.rentPointId] = rentPointEntityId
                it[productType] = product.productType
                it[article] = product.article
                it[printType] = product.printType
                it[color] = product.color
                it[formFactor] = product.formFactor
                it[size] = product.size
                it[condition] = product.condition ?: ProductCondition.READY
            }
        }
    }

    // Используется только для генерации датасетов
    fun generateProducts(rentPointId: Long, itemsNumber: Int) {
        val products: ArrayList<Product> = arrayListOf()
        val rentPointEntityId = EntityID(rentPointId, RentPointTable)
        repeat(itemsNumber) { products.add(generateProduct().withGeneratedArticul()) }
        log.info("generated product for rentPointId=${ProductTable.rentPointId}, size=${products.size}, products: $products")
        transaction {
            ProductTable.batchInsert(products) { product ->
                this[ProductTable.rentPointId] = rentPointEntityId
                this[productType] = product.productType
                this[article] = product.article
                this[printType] = product.printType
                this[color] = product.color
                this[formFactor] = product.formFactor
                this[size] = product.size
                this[condition] = product.condition ?: ProductCondition.READY
            }
        }
    }

    fun updateCondition(productId: Long, condition: ProductCondition) {
        transaction {
            ProductTable.update({ ProductTable.id eq productId }) {
                it[ProductTable.condition] = condition
            }
        }
    }
}