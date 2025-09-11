package com.wodwiki.tv.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button as TvButton
import androidx.tv.material3.Card as TvCard
import com.wodwiki.tv.R
import timber.log.Timber

data class MenuItem(
    val title: String,
    val description: String,
    val onClick: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var jsonData by remember { mutableStateOf("{}") }
    
    val menuItems = listOf(
        MenuItem(
            title = stringResource(R.string.json_data_display),
            description = "Display and interact with JSON domain model data"
        ) {
            Timber.d("JSON Data Display clicked")
            selectedTab = 1
        },
        MenuItem(
            title = stringResource(R.string.heart_rate_monitor),
            description = "Connect to Bluetooth heart rate monitor"
        ) {
            Timber.d("Heart Rate Monitor clicked")
            selectedTab = 2
        },
        MenuItem(
            title = stringResource(R.string.react_webview),
            description = "Host React application for shared code"
        ) {
            Timber.d("React WebView clicked")
            selectedTab = 3
        },
        MenuItem(
            title = stringResource(R.string.chromecast_receiver),
            description = "Receive Chromecast events from website"
        ) {
            Timber.d("Chromecast Receiver clicked")
            selectedTab = 4
        }
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp), // Overscan-safe margin
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        when (selectedTab) {
            0 -> MainMenuScreen(menuItems)
            1 -> JsonDataScreen(
                jsonData = jsonData,
                onJsonDataChange = { jsonData = it },
                onBack = { selectedTab = 0 }
            )
            2 -> HeartRateScreen(onBack = { selectedTab = 0 })
            3 -> ReactWebViewScreen(onBack = { selectedTab = 0 })
            4 -> ChromecastReceiverScreen(
                onJsonReceived = { jsonData = it },
                onBack = { selectedTab = 0 }
            )
        }
    }
}

@Composable
fun MainMenuScreen(menuItems: List<MenuItem>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(menuItems) { item ->
            MenuCard(item)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuCard(item: MenuItem) {
    val focusRequester = remember { FocusRequester() }
    
    Card(
        onClick = item.onClick,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}