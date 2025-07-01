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
            GymTvApp() // Set the GymTvApp as the root composable
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun GymTvApp() {
    // Apply the TV Material Theme at the root of the application's UI
    MaterialTheme {
        // TimerScreen will be the main content.
        // ViewModel providing actual data would be injected or created here in a real scenario.
        TimerScreen()
    }
}

// Opt-in for ExperimentalTvMaterial3Api is required
// This preview now reflects the actual app structure.
@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = "id:tv_1080p")
@Composable
fun DefaultPreview() {
    GymTvApp()
}
