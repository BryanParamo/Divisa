package com.example.divisa.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*


@Entity(tableName = "exchange_rates")
data class ExchangeRate(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val currency: String,  // Código de la moneda (USD, EUR, etc.)
    val rate: Double,       // Tasa de cambio
    val timestamp: Long     // Momento en que se guardó
)




