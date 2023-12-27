package com.example.twcurrencyexchanger.data

import com.example.twcurrencyexchanger.Database
import com.example.twcurrencyexchanger.core.database.DatabaseDriverFactory
import com.example.twcurrencyexchanger.core.di.Inject.instance
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val databaseModule = DI.Module("databaseModule") {
    bind<DatabaseDriverFactory>() with singleton {
        DatabaseDriverFactory(instance())
    }

    bind<Database>() with singleton {
        val driverFactory: DatabaseDriverFactory = instance()
        val driver = driverFactory.createDriver("exchanger.db")
        Database(driver)
    }
}