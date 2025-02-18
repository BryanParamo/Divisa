package com.example.divisa.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExchangeRateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeRate(rate: ExchangeRate)

    @Query("SELECT * FROM exchange_rates ORDER BY timestamp DESC")
    suspend fun getAllExchangeRates(): List<ExchangeRate>
}
