package com.example.divisa.data

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object SyncManager {
    fun scheduleSync(context: Context) {
        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ExchangeRateWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
        WorkManager.getInstance(context.applicationContext)
            .enqueue(oneTimeWorkRequest)
    }
}


