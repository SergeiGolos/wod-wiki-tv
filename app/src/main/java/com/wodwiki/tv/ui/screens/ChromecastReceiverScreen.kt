package com.wodwiki.tv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.wodwiki.tv.R
import timber.log.Timber

data class ChromecastMessage(
    val timestamp: Long,
    val type: String,
    val data: String
)

@Composable
fun ChromecastReceiverScreen(
    onJsonReceived: (String) -> Unit,
    onBack: () -> Unit
) {
    var isListening by remember { mutableStateOf(false) }
    var receivedMessages by remember { mutableStateOf(listOf<ChromecastMessage>()) }
    var lastJsonData by remember { mutableStateOf("") }
    
    // Simulate receiving Chromecast data
    LaunchedEffect(isListening) {
        if (isListening) {
            kotlinx.coroutines.delay(3000)
            val sampleData = """
                {
                    "action": "start_workout",
                    "workout": {
                        "name": "Morning HIIT",
                        "duration": 1200,
                        "exercises": [
                            {"name": "Jumping Jacks", "duration": 30},
                            {"name": "Push-ups", "duration": 30},
                            {"name": "Squats", "duration": 30}
                        ]
                    },
                    "user": {
                        "id": "user123",
                        "name": "John Doe"
                    }
                }
            """.trimIndent()
            
            val message = ChromecastMessage(
                timestamp = System.currentTimeMillis(),
                type = "workout_data",
                data = sampleData
            )
            
            receivedMessages = receivedMessages + message
            lastJsonData = sampleData
            onJsonReceived(sampleData)
            Timber.d("Simulated Chromecast data received")
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.chromecast_receiver),
                style = MaterialTheme.typography.titleLarge
            )
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Status and controls
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Receiver Status",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = if (isListening) "Listening for Chromecast events" else "Not listening",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isListening) Color.Green else Color.Red
                        )
                    }
                    
                    Button(
                        onClick = { 
                            isListening = !isListening
                            if (!isListening) {
                                receivedMessages = emptyList()
                            }
                            Timber.d("Chromecast receiver toggled: $isListening")
                        }
                    ) {
                        Text(if (isListening) "Stop Listening" else "Start Listening")
                    }
                }
            }
        }
        
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Messages list
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Received Messages (${receivedMessages.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Card(
                    modifier = Modifier.fillMaxHeight(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    if (receivedMessages.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (isListening) "Waiting for Chromecast events..." else "Start listening to receive events",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(receivedMessages.reversed()) { message ->
                                MessageItem(message)
                            }
                        }
                    }
                }
            }
            
            // Latest data preview
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Latest Data Preview",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Card(
                    modifier = Modifier.fillMaxHeight(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            if (lastJsonData.isNotEmpty()) {
                                Text(
                                    text = formatJson(lastJsonData),
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            } else {
                                Text(
                                    text = "No data received yet",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: ChromecastMessage) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = message.type,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
                        .format(java.util.Date(message.timestamp)),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = message.data.take(100) + if (message.data.length > 100) "..." else "",
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

private fun formatJson(json: String): String {
    return try {
        val gson = Gson()
        val jsonElement = gson.fromJson(json, Any::class.java)
        gson.toJson(jsonElement)
    } catch (e: JsonSyntaxException) {
        json
    }
}