package com.olga.avshister.domain

import com.olga.avshister.features.rent.RentDTO

data class Rent(
    //val id: Long? = null,
    val customerId: Long? = null, // id клиента, не отправляем на бекенд или игнорируем там (должен сам определяться по токену),
    val startedAt: Long, // время начала аренды в Unix-формате,
    val finishedAt: Long? = null, // время завершения аренды (если аренда уже завершена, оплачена),
    val startRentPointId: Long, // id точки аренды
    val finishRentPointId: Long? = null, // id точки возврата
    val productIds: List<Long>, // id товаров, взятых в аренду,
    val cardNumber: String, // номер выбранной карта для последующей оплаты
    val rate: Rate, // выбранный тариф
) {
    enum class Rate(val textValue: String, val priceValue: Int, val timeUnit: String) {
        PER_MINUTE ("Поминутный", priceValue = 1, timeUnit = "мин"),
        PER_HOUR ("Почасовой", priceValue = 50, timeUnit = "час"),
        PER_DAY ("Посуточный", priceValue = 350, timeUnit = "день"),
    }

    companion object {
        fun Rent.toDTO(): RentDTO {
            return RentDTO(
                startedAt = startedAt,
                finishedAt = finishedAt,
                startRentPointId = this.startRentPointId,
                finishRentPointId = this.finishRentPointId,
                productIds = this.productIds,
                cardNumber = this.cardNumber,
                rate = this.rate,
            )
        }
    }
}