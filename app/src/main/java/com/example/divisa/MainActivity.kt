package com.example.divisa

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.divisa.data.ExchangeRate
import com.example.divisa.data.ExchangeRateViewModel
import com.example.divisa.data.SyncManager
import com.example.divisa.ui.theme.DivisaTheme

class MainActivity : ComponentActivity() {

    private val exchangeRateViewModel: ExchangeRateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "onCreate ejecutado")

        // Programar el Worker para obtener las tasas de cambio
        SyncManager.scheduleSync(applicationContext)

        setContent {
            DivisaTheme {
                // UI de la app
                ExchangeRateList(exchangeRateViewModel)
            }
        }
    }
}

@Composable
fun ExchangeRateList(viewModel: ExchangeRateViewModel) {
    val exchangeRates = viewModel.exchangeRates.observeAsState(emptyList())
    Log.d("MainActivity", "Tasas obtenidas (antes de mostrar): ${exchangeRates.value}")

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Tasas de cambio", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(exchangeRates.value) { rate ->
                ExchangeRateItem(rate)
            }
        }
    }
}

@Composable
fun ExchangeRateItem(rate: ExchangeRate) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = "Moneda: ${rate.currency}")
        Text(text = "Tasa: ${rate.rate}")
    }
}
