package com.wodwiki.tv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wodwiki.tv.R
import timber.log.Timber

@Composable
fun HeartRateScreen(onBack: () -> Unit) {
    var heartRate by remember { mutableStateOf(0) }
    var isConnected by remember { mutableStateOf(false) }
    var connectionStatus by remember { mutableStateOf("Disconnected") }
    
    // Simulate heart rate data for now
    LaunchedEffect(isConnected) {
        if (isConnected) {
            connectionStatus = "Connected"
            // Simulate varying heart rate
            kotlinx.coroutines.delay(1000)
            heartRate = (60..100).random()
        } else {
            connectionStatus = "Disconnected"
            heartRate = 0
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.heart_rate_monitor),
                style = MaterialTheme.typography.titleLarge
            )
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Connection status
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = connectionStatus,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isConnected) Color.Green else Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Button(
                    onClick = { 
                        isConnected = !isConnected
                        Timber.d("Heart rate connection toggled: $isConnected")
                    }
                ) {
                    Text(if (isConnected) stringResource(R.string.disconnect) else stringResource(R.string.connect))
                }
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Heart rate display
        if (isConnected) {
            Card(
                modifier = Modifier
                    .size(300.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when {
                        heartRate < 60 -> Color(0xFF4CAF50) // Normal
                        heartRate < 85 -> Color(0xFFFF9800) // Elevated
                        else -> Color(0xFFF44336) // High
                    }
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "$heartRate",
                        fontSize = 72.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "BPM",
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(horizontal = 32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Connect to a heart rate monitor to see live data",
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Instructions
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Instructions:",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "• Ensure Bluetooth is enabled on your Android TV\n" +
                          "• Put your heart rate monitor in pairing mode\n" +
                          "• Click Connect to scan for nearby devices\n" +
                          "• Compatible with standard Bluetooth heart rate monitors",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}