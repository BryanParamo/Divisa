package com.example.divisa.data

class ExchangeRateRepository(private val dao: ExchangeRateDao) {
    suspend fun insertExchangeRate(rate: ExchangeRate) {
        dao.insertExchangeRate(rate)
    }

    suspend fun getAllExchangeRates(): List<ExchangeRate> {
        return dao.getAllExchangeRates()
    }
}
