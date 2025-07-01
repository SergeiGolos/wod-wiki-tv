package com.example.gymtvapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.ExperimentalTvMaterial3Api // Required for TvMaterial3 components
import androidx.tv.material3.MaterialTheme // Use TV Material Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Using TvMaterialTheme for TV-specific styling
            MainAppScreen()
        }
    }
}

// Opt-in for ExperimentalTvMaterial3Api is required
@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun MainAppScreen() {
    // Using TV Material Theme
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder Text. This will be replaced by the main timer screen UI.
            Text(text = "Welcome to WOD Wiki TV!")
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = "id:tv_1080p")
@Composable
fun DefaultPreview() {
    MaterialTheme {
        MainAppScreen()
    }
}
