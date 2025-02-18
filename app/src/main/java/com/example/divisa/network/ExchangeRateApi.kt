package com.example.divisa.network

import retrofit2.http.GET
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Definir la interfaz Retrofit
interface ExchangeRateApi {
    @GET("latest")
    fun getExchangeRates(): Call<ExchangeRatesResponse> // Cambia esto según la respuesta de la API
}

// Configurar Retrofit para usar la API
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/27e09a3c57b788cdbce385e7/") // Cambia tu API_KEY aquí
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitService: ExchangeRateApi by lazy {
        retrofit.create(ExchangeRateApi::class.java)
    }
}
