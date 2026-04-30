package com.olga.avshister

import com.olga.avshister.database.table.CardTable
import com.olga.avshister.database.table.ProductTable
import com.olga.avshister.database.table.RentPointTable
import com.olga.avshister.database.table.RentTable
import com.olga.avshister.database.table.TokenTable
import com.olga.avshister.database.table.UserTable
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.server.application.log
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

fun Application.configureDatabases() {

    val databaseUrl = System.getenv("DATABASE_URL")

    if (databaseUrl != null) {
        log.info("Используем Railway PostgreSQL")
        connectRemoteDatabase(databaseUrl)
    } else {
        log.info("Используем локальную БД")
        connectLocalDatabase()
    }

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

private fun connectRemoteDatabase(databaseUrl: String) {
    val jdbcUrl = databaseUrl
        .replace("postgres://", "jdbc:postgresql://")
        .replace("postgresql://", "jdbc:postgresql://")

    val config = HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 10
        isAutoCommit = false
    }

    val dataSource = HikariDataSource(config)
    Database.connect(dataSource)

    createTables()
}

private fun connectLocalDatabase() {
    Database.connect(
        url = "jdbc:postgresql://localhost:5432/RainGuardLocal",
        driver = "org.postgresql.Driver",
        user = "user_server",
        password = "" // для локальной БД пароль не нужен, только user
    )

    createTables()
}

private fun createTables() {
    transaction {
        // если перечисленных таблиц еще нет, то они будут созданы
        SchemaUtils.create(UserTable)
        SchemaUtils.create(TokenTable)
        SchemaUtils.create(ProductTable)
        SchemaUtils.create(RentPointTable)
        SchemaUtils.create(CardTable)
        SchemaUtils.create(RentTable)
    }
}