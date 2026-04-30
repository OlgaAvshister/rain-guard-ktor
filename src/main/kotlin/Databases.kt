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
import java.net.URI

fun Application.configureDatabases() {

    val databaseUrl = System.getenv("DATABASE_URL")
        ?: error("DATABASE_URL is missing (Railway env not configured)")

    log.info("Using Railway DB")

    val dataSource = createDataSource(databaseUrl)

    Database.connect(dataSource)

    createTables()

    log.info("DB connected successfully")
}

/**
 * Парсит Railway URL:
 * postgresql://user:password@host:5432/db
 * → JDBC + username/password отдельно
 */
private fun createDataSource(databaseUrl: String): HikariDataSource {

    val uri = URI(databaseUrl)

    val host = uri.host
    val port = uri.port
    val database = uri.path.removePrefix("/")

    val userInfo = uri.userInfo?.split(":")
    val username = userInfo?.getOrNull(0)
    val password = userInfo?.getOrNull(1)

    val jdbcUrl = "jdbc:postgresql://$host:$port/$database"

    val config = HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        driverClassName = "org.postgresql.Driver"

        this.username = username
        this.password = password

        maximumPoolSize = 10
        isAutoCommit = false

        initializationFailTimeout = 10000
        connectionTimeout = 10000
        validationTimeout = 5000
    }

    return HikariDataSource(config)
}

private fun createTables() {
    transaction {
        SchemaUtils.create(
            UserTable,
            TokenTable,
            ProductTable,
            RentPointTable,
            CardTable,
            RentTable
        )
    }
}