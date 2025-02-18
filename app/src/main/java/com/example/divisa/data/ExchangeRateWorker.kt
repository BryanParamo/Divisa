package com.example.divisa.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.divisa.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExchangeRateWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            // Hacer la solicitud de red
            val response = RetrofitInstance.retrofitService.getExchangeRates().execute()

            // Verificar si la respuesta es exitosa
            if (response.isSuccessful) {
                val rates = response.body()?.rates ?: return Result.retry()

                // Guardar los datos en la base de datos
                val database = AppDatabase.getDatabase(applicationContext)
                val dao = database.exchangeRateDao()

                rates.forEach { (currency, rate) ->
                    val exchangeRate = ExchangeRate(
                        currency = currency,
                        rate = rate,
                        timestamp = System.currentTimeMillis()
                    )
                    dao.insertExchangeRate(exchangeRate)
                }

                Result.success()
            } else {
                Result.retry() // Reintentar si no fue exitoso
            }
        } catch (e: Exception) {
            Result.retry() // Reintentar si hubo un error
        }
    }
}
