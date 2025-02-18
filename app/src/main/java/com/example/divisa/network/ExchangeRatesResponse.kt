package com.example.divisa.network

data class ExchangeRatesResponse(
    val rates: Map<String, Double> // O cualquier estructura que se ajuste a los datos que devuelve la API
)
