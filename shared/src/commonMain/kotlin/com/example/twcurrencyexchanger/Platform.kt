package com.example.twcurrencyexchanger

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform