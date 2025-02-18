package com.example.divisa.data

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object SyncManager {
    fun scheduleSync(context: Context) {
        val workRequest = PeriodicWorkRequestBuilder<ExchangeRateWorker>(1, TimeUnit.HOURS)
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED) // Solo si hay internet
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "exchange_rate_sync",
            ExistingPeriodicWorkPolicy.KEEP, // Mantiene la tarea si ya está programada
            workRequest
        )
    }
}
