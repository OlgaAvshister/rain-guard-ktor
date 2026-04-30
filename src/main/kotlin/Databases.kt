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
        ?: error("DATABASE_URL is missing (Railway env not configured)")

    log.info("Using Railway DB")

    val jdbcUrl = databaseUrl.replace("postgres://", "jdbc:postgresql://")

    val config = HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        driverClassName = "org.postgresql.Driver"
        maximumPoolSize = 10
        isAutoCommit = false
    }

    Database.connect(HikariDataSource(config))

    createTables()

    log.info("DB connected successfully")
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