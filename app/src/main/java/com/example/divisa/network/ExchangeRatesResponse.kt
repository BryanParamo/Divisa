package com.example.divisa.network

import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("conversion_rates")
    val rates: Map<String, Double>
)

