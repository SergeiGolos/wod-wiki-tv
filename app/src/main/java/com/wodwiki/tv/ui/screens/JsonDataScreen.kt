package com.wodwiki.tv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.wodwiki.tv.R
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JsonDataScreen(
    jsonData: String,
    onJsonDataChange: (String) -> Unit,
    onBack: () -> Unit
) {
    var editableJson by remember { mutableStateOf(jsonData) }
    var isValidJson by remember { mutableStateOf(true) }
    var formattedJson by remember { mutableStateOf("") }
    
    // Validate and format JSON
    LaunchedEffect(editableJson) {
        try {
            val gson = Gson()
            val jsonElement = gson.fromJson(editableJson, Any::class.java)
            formattedJson = gson.toJson(jsonElement)
            isValidJson = true
            onJsonDataChange(editableJson)
        } catch (e: JsonSyntaxException) {
            isValidJson = false
            Timber.w("Invalid JSON: ${e.message}")
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
                text = stringResource(R.string.json_data_display),
                style = MaterialTheme.typography.titleLarge
            )
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Input column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "JSON Input",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                OutlinedTextField(
                    value = editableJson,
                    onValueChange = { editableJson = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    isError = !isValidJson,
                    supportingText = {
                        if (!isValidJson) {
                            Text(
                                text = stringResource(R.string.invalid_json),
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = {
                        editableJson = """
                            {
                                "type": "workout",
                                "name": "HIIT Session",
                                "duration": 1800,
                                "exercises": [
                                    {
                                        "name": "Burpees",
                                        "duration": 30,
                                        "rest": 10
                                    },
                                    {
                                        "name": "Mountain Climbers", 
                                        "duration": 30,
                                        "rest": 10
                                    }
                                ],
                                "heartRateZones": {
                                    "warmup": "50-60%",
                                    "workout": "70-85%",
                                    "cooldown": "50-60%"
                                }
                            }
                        """.trimIndent()
                    }
                ) {
                    Text("Load Sample Data")
                }
            }
            
            // Display column
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Formatted JSON Output",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        item {
                            if (isValidJson && formattedJson.isNotEmpty()) {
                                Text(
                                    text = formattedJson,
                                    fontFamily = FontFamily.Monospace,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            } else {
                                Text(
                                    text = "Invalid or empty JSON",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}