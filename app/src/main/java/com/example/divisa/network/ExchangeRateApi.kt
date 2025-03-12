package com.example.divisa.network

import retrofit2.Call
import retrofit2.http.GET

// Definir la interfaz Retrofit
interface ExchangeRateApi {
    @GET("latest/USD")
    fun getExchangeRates(): Call<ExchangeRatesResponse>
}




