package com.example.divisa.data

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.divisa.network.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.divisa.data.AppDatabase


class ExchangeRateWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    // Instancia de DAO (Data Access Object) para insertar datos en la base de datos
    private val dao = AppDatabase.getDatabase(appContext).exchangeRateDao()

    override suspend fun doWork(): Result {
        Log.d("ExchangeRateWorker", "Trabajando para obtener las tasas de cambio")
        return try {
            // Llamada sincrónica con Retrofit en el contexto IO
            val response = withContext(Dispatchers.IO) {
                RetrofitInstance.retrofitService.getExchangeRates().execute()
            }
            Log.d("ExchangeRateWorker", "Response code: ${response.code()}")
            Log.d("ExchangeRateWorker", "Response body: ${response.body()}")

            if (response.isSuccessful) {
                val rates = response.body()?.rates
                if (rates == null) {
                    Log.e("ExchangeRateWorker", "El body es nulo o no contiene conversion_rates")
                    return Result.failure()
                }
                Log.d("ExchangeRateWorker", "Tasas obtenidas: $rates")
                rates.forEach { (currency, rate) ->
                    val exchangeRate = ExchangeRate(
                        currency = currency,
                        rate = rate,
                        timestamp = System.currentTimeMillis()
                    )
                    dao.insertExchangeRate(exchangeRate)
                    Log.d("ExchangeRateWorker", "Insertado: $exchangeRate")
                }
                Result.success()
            } else {
                Log.e("ExchangeRateWorker", "Error al obtener tasas: ${response.errorBody()?.string()}")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.e("ExchangeRateWorker", "Error en la solicitud: ${e.message}")
            Result.failure()
        }
    }

}
