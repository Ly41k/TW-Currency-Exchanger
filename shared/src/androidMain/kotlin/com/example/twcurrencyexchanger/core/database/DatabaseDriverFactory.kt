package com.example.twcurrencyexchanger.core.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.twcurrencyexchanger.Database
import com.example.twcurrencyexchanger.core.di.PlatformConfiguration

class DatabaseDriverFactory (private val platformConfiguration: PlatformConfiguration) {
    fun createDriver(name: String): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, platformConfiguration.applicationContext, name)
    }
}
