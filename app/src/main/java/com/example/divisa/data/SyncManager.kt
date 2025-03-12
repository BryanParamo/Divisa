package com.example.divisa.data

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object SyncManager {
    fun scheduleSync(context: Context) {
        // 1. Trabajo inmediato para la primera sincronización
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ExchangeRateWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS) // Retraso corto para pruebas
            .build()
        WorkManager.getInstance(context.applicationContext)
            .enqueue(oneTimeWorkRequest)

        // 2. Trabajo periódico para actualizaciones cada hora
        val periodicWorkRequest = PeriodicWorkRequestBuilder<ExchangeRateWorker>(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .build()
        WorkManager.getInstance(context.applicationContext)
            .enqueueUniquePeriodicWork(
                "exchange_rate_sync",
                ExistingPeriodicWorkPolicy.KEEP, // Mantiene el trabajo si ya existe
                periodicWorkRequest
            )
    }
}



