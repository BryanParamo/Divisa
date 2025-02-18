package com.example.divisa.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class ExchangeRateViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).exchangeRateDao()

    val exchangeRates: LiveData<List<ExchangeRate>> = flow {
        emit(dao.getAllExchangeRates())
    }.asLiveData()
}
