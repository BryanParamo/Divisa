package com.example.divisa

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.divisa.data.ExchangeRate
import com.example.divisa.data.ExchangeRateViewModel
import com.example.divisa.data.SyncManager
import com.example.divisa.ui.theme.DivisaTheme

class MainActivity : ComponentActivity() {
    private val viewModel: ExchangeRateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Iniciar sincronización automática
        SyncManager.scheduleSync(this)

        setContent {
            DivisaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ExchangeRateScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ExchangeRateScreen(viewModel: ExchangeRateViewModel, modifier: Modifier = Modifier) {
    val exchangeRates by viewModel.exchangeRates.observeAsState(emptyList())

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Tasas de Cambio", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(exchangeRates) { rate ->
                ExchangeRateItem(rate)
            }
        }
    }
}

@Composable
fun ExchangeRateItem(rate: ExchangeRate) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Moneda: ${rate.currency}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Tasa: ${rate.rate}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExchangeRateScreen() {
    DivisaTheme {
        ExchangeRateScreen(viewModel = ExchangeRateViewModel(Application()))
    }
}
