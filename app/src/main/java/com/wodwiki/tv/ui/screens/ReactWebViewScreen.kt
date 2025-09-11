package com.wodwiki.tv.ui.screens

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.wodwiki.tv.R
import timber.log.Timber

@Composable
fun ReactWebViewScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    var webView: WebView? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(true) }
    
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
                text = stringResource(R.string.react_webview),
                style = MaterialTheme.typography.titleLarge
            )
            Button(onClick = onBack) {
                Text("Back")
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        if (isLoading) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        Text(
                            text = "Loading React Application...",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
        
        // WebView for React app
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading = false
                            Timber.d("WebView page finished loading: $url")
                        }
                    }
                    
                    settings.apply {
                        javaScriptEnabled = true
                        domStorageEnabled = true
                        allowFileAccess = true
                        allowContentAccess = true
                    }
                    
                    // Add JavaScript interface for communication
                    addJavascriptInterface(WebAppInterface(), "Android")
                    
                    // Load the React app (placeholder HTML for now)
                    loadDataWithBaseURL(
                        null,
                        getReactAppHtml(),
                        "text/html",
                        "UTF-8",
                        null
                    )
                    
                    webView = this
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    webView?.evaluateJavascript("sendDataToReact('Hello from Android TV!')", null)
                    Timber.d("Sent data to React app")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Send Data to React")
            }
            
            Button(
                onClick = {
                    webView?.reload()
                    isLoading = true
                    Timber.d("Reloading React app")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Reload React App")
            }
        }
    }
}

class WebAppInterface {
    @JavascriptInterface
    fun sendToAndroid(message: String) {
        Timber.d("Received from React: $message")
    }
    
    @JavascriptInterface
    fun showToast(message: String) {
        Timber.d("Toast from React: $message")
    }
}

private fun getReactAppHtml(): String {
    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>React App</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    margin: 0;
                    padding: 20px;
                    background-color: #121212;
                    color: white;
                    font-size: 18px;
                }
                .container {
                    max-width: 800px;
                    margin: 0 auto;
                }
                .button {
                    background-color: #1976D2;
                    color: white;
                    border: none;
                    padding: 12px 24px;
                    font-size: 16px;
                    border-radius: 8px;
                    cursor: pointer;
                    margin: 8px;
                }
                .button:hover {
                    background-color: #0D47A1;
                }
                .status {
                    background-color: #1E1E1E;
                    padding: 16px;
                    border-radius: 8px;
                    margin: 16px 0;
                }
                .heart-rate {
                    font-size: 48px;
                    color: #4CAF50;
                    text-align: center;
                    margin: 20px 0;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>WoD Wiki TV - React Integration</h1>
                <p>This is a React application running inside an Android TV WebView.</p>
                
                <div class="status">
                    <h3>Connection Status</h3>
                    <p id="status">Ready</p>
                </div>
                
                <div class="heart-rate" id="heartRate">-- BPM</div>
                
                <button class="button" onclick="sendToAndroid()">Send Data to Android</button>
                <button class="button" onclick="simulateHeartRate()">Simulate Heart Rate</button>
                <button class="button" onclick="clearDisplay()">Clear Display</button>
                
                <div class="status">
                    <h3>Received Data</h3>
                    <pre id="receivedData">No data received yet</pre>
                </div>
            </div>
            
            <script>
                function sendToAndroid() {
                    if (window.Android) {
                        window.Android.sendToAndroid('Hello from React!');
                        document.getElementById('status').innerText = 'Data sent to Android';
                    }
                }
                
                function sendDataToReact(data) {
                    document.getElementById('receivedData').innerText = data;
                    document.getElementById('status').innerText = 'Data received from Android';
                }
                
                function simulateHeartRate() {
                    const heartRate = Math.floor(Math.random() * 40) + 60; // 60-100 BPM
                    document.getElementById('heartRate').innerText = heartRate + ' BPM';
                    document.getElementById('status').innerText = 'Heart rate simulated: ' + heartRate + ' BPM';
                }
                
                function clearDisplay() {
                    document.getElementById('heartRate').innerText = '-- BPM';
                    document.getElementById('receivedData').innerText = 'No data received yet';
                    document.getElementById('status').innerText = 'Display cleared';
                }
                
                // Initialize
                document.getElementById('status').innerText = 'React app loaded successfully';
            </script>
        </body>
        </html>
    """.trimIndent()
}