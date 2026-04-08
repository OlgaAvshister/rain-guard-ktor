package com.olga.avshister

import com.olga.avshister.database.users.Users
import io.ktor.server.application.Application
import io.ktor.server.application.log
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabases() {
    configureLocalDatabase()

    try {
        transaction {
            // Простой запрос, который не требует существования таблиц, просто пинг таблицы
            exec("SELECT 1")
        }
        log.info("Подключение к БД успешно!")
    } catch (e: Exception) {
        log.error("Ошибка подключения к БД: ${e.message}")
        e.printStackTrace()
    }
}

fun configureLocalDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/RainGuardLocal",
        driver = "org.postgresql.Driver",
        user = "user_server",
        password = "" // для локальной БД пароль не нужен, только user
    )

    transaction {
        // если перечисленных таблиц еще нет, то они будут созданы
        SchemaUtils.create(Users)
    }
}