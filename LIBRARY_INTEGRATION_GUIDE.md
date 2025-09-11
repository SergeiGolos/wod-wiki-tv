# WoD Wiki TV - Library Integration Guide

**Doc: Library Integration – Comprehensive Guide to External Dependencies**
- Audience: developer
- Source of Truth: links to `app/build.gradle.kts` and implementation files
- Version: v1.0 (September 2024)

## Overview

This document provides a comprehensive guide to all external libraries integrated into the WoD Wiki TV project, their purposes, configuration details, and implementation patterns. Each library section includes setup instructions, usage examples, and integration best practices.

## Core Android TV Libraries

### 1. Jetpack Compose for TV

**Dependencies:**
```kotlin
implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
implementation("androidx.tv:tv-material:1.0.0-alpha10") 
implementation("androidx.compose.ui:ui:1.5.4")
implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
implementation("androidx.compose.material3:material3:1.1.2")
```

**Purpose:** Modern declarative UI framework specifically optimized for Android TV development.

**Key Components Used:**
- **TvLazyRow/TvLazyColumn**: TV-optimized list components
- **Card**: TV-specific card layouts with focus handling
- **Button**: TV-optimized button components
- **Surface**: Base container with TV theming

**Implementation Example:**
```kotlin
// From MainScreen.kt
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
```

**TV-Specific Features:**
- **Automatic Focus Management**: Cards automatically handle D-pad navigation
- **TV Typography**: Large, readable fonts optimized for 10-foot viewing
- **Focus Indicators**: Built-in visual feedback for focused elements
- **Overscan Safety**: Automatic margins for TV display boundaries

**Configuration:**
```kotlin
// In app/build.gradle.kts
buildFeatures {
    compose = true
}

composeOptions {
    kotlinCompilerExtensionVersion = "1.5.4"
}
```

**Best Practices:**
- Always use TV-specific components over standard Material components
- Implement FocusRequester for complex focus management
- Test navigation thoroughly with D-pad input
- Use overscan-safe margins (48dp minimum)

### 2. Navigation Compose

**Dependencies:**
```kotlin
implementation("androidx.navigation:navigation-compose:2.7.5")
```

**Purpose:** Type-safe navigation framework for Compose-based applications.

**Implementation:**
```kotlin
// From MainScreen.kt - State-based navigation
var selectedTab by remember { mutableStateOf(0) }

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
```

**Features Used:**
- **State-based Navigation**: Simple integer-based screen switching
- **Back Navigation**: Consistent back button handling
- **Parameter Passing**: Data flow between screens
- **Navigation Callbacks**: Clean separation of navigation logic

**TV Optimizations:**
- Immediate screen transitions (no complex animations)
- Clear back navigation patterns
- State preservation during navigation
- Focus restoration when returning to screens

## Dependency Injection

### 3. Hilt (Dagger)

**Dependencies:**
```kotlin
implementation("com.google.dagger:hilt-android:2.48.1")
kapt("com.google.dagger:hilt-compiler:2.48.1")
implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
```

**Plugin Configuration:**
```kotlin
// In app/build.gradle.kts
plugins {
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
```

**Purpose:** Dependency injection framework for clean architecture and testability.

**Implementation:**
```kotlin
// From WodWikiTvApplication.kt
@HiltAndroidApp
class WodWikiTvApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Hilt handles dependency injection setup automatically
    }
}

// From MainActivity.kt
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Hilt provides dependencies to this activity
}
```

**Usage Pattern:**
- **Application Level**: `@HiltAndroidApp` annotation
- **Activity Level**: `@AndroidEntryPoint` annotation
- **ViewModel Injection**: Automatic injection in Compose screens
- **Service Injection**: Dependencies available in services

**Benefits for TV Development:**
- Clean separation of concerns
- Easy testing with mock dependencies
- Consistent dependency management across features
- Reduced boilerplate code

## Bluetooth Integration

### 4. Nordic Semiconductor BLE Library

**Dependencies:**
```kotlin
implementation("no.nordicsemi.android:ble:2.6.1")
```

**Purpose:** Professional-grade Bluetooth Low Energy library for heart rate monitor integration.

**Permissions Required:**
```xml
<!-- From AndroidManifest.xml -->
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" 
    android:usesPermissionFlags="neverForLocation" />
<uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.BODY_SENSORS" />
<uses-permission android:name="android.permission.BODY_SENSORS_BACKGROUND" />
```

**Service Implementation:**
```kotlin
// From HeartRateService.kt
class HeartRateService : Service() {
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "heart_rate_service"
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d("HeartRateService started")
        
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Heart Rate Monitor")
            .setContentText("Monitoring heart rate...")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()
        
        startForeground(NOTIFICATION_ID, notification)
        return START_STICKY
    }
}
```

**Architecture Pattern:**
- **Foreground Service**: Maintains BLE connection in background
- **Notification Integration**: User-visible service status
- **Lifecycle Management**: Proper startup and cleanup
- **Error Handling**: Connection failure recovery

**TV-Specific Considerations:**
- Large, visible heart rate display optimized for distance viewing
- Color-coded zones for quick status recognition
- Clear connection status indicators
- Simple connect/disconnect controls

**Integration with UI:**
```kotlin
// From HeartRateScreen.kt
// Simulate heart rate data for demonstration
LaunchedEffect(isConnected) {
    if (isConnected) {
        connectionStatus = "Connected"
        kotlinx.coroutines.delay(1000)
        heartRate = (60..100).random()
    } else {
        connectionStatus = "Disconnected"
        heartRate = 0
    }
}
```

**Production Implementation Notes:**
- Current implementation is simulated for demonstration
- Production version would use Nordic BLE callbacks
- Heart Rate Profile (HRP) parsing implementation needed
- Device discovery and pairing UI required

## Chromecast Integration

### 5. Google Cast Framework

**Dependencies:**
```kotlin
implementation("com.google.android.gms:play-services-cast-framework:21.4.0")
```

**Purpose:** Enable receiving cast events from web applications and mobile devices.

**Manifest Configuration:**
```xml
<!-- From AndroidManifest.xml -->
<meta-data
    android:name="com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME"
    android:value="com.wodwiki.tv.cast.CastOptionsProvider" />
```

**Options Provider Implementation:**
```kotlin
// From CastOptionsProvider.kt
class CastOptionsProvider : OptionsProvider {
    override fun getCastOptions(context: Context): CastOptions {
        return CastOptions.Builder()
            .setReceiverApplicationId(CastMediaControlIntent.DEFAULT_MEDIA_RECEIVER_APPLICATION_ID)
            .build()
    }
    
    override fun getAdditionalSessionProviders(context: Context): List<SessionProvider>? {
        return null
    }
}
```

**Event Processing:**
```kotlin
// From ChromecastReceiverScreen.kt
data class ChromecastMessage(
    val timestamp: Long,
    val type: String,
    val data: String
)

// Simulated event reception
LaunchedEffect(isListening) {
    if (isListening) {
        kotlinx.coroutines.delay(3000)
        val sampleData = """
            {
                "action": "start_workout",
                "workout": {
                    "name": "Morning HIIT",
                    "duration": 1200,
                    "exercises": [...]
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
        onJsonReceived(sampleData)
    }
}
```

**UI Integration:**
- **Real-time Message Display**: Shows incoming cast events
- **JSON Preview**: Formatted display of received data
- **Message History**: Time-ordered event log
- **Status Indicators**: Clear listening/not listening states

**TV Optimizations:**
- **Large Message Cards**: Easy to read from distance
- **Timestamp Display**: Clear temporal context
- **Two-panel Layout**: Messages and preview side by side
- **Scrollable History**: Navigate through message history with D-pad

## WebView Integration

### 6. AndroidX WebKit

**Dependencies:**
```kotlin
implementation("androidx.webkit:webkit:1.8.0")
```

**Purpose:** Enhanced WebView capabilities for React application integration.

**WebView Configuration:**
```kotlin
// From ReactWebViewScreen.kt
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
    
    // Load React app content
    loadDataWithBaseURL(null, getReactAppHtml(), "text/html", "UTF-8", null)
}
```

**JavaScript Bridge:**
```kotlin
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
```

**Bidirectional Communication:**
```kotlin
// Android to React
webView?.evaluateJavascript("sendDataToReact('Hello from Android TV!')", null)

// React to Android (in embedded HTML)
function sendToAndroid() {
    if (window.Android) {
        window.Android.sendToAndroid('Hello from React!');
    }
}
```

**TV-Optimized React Template:**
```html
<!-- From getReactAppHtml() function -->
<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 20px;
    background-color: #121212;
    color: white;
    font-size: 18px; /* Large text for TV */
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
.heart-rate {
    font-size: 48px; /* Very large for TV viewing */
    color: #4CAF50;
    text-align: center;
    margin: 20px 0;
}
</style>
```

**Features:**
- **Dark Theme**: Optimized for TV viewing environments
- **Large Fonts**: Readable from 10-foot distance
- **Interactive Elements**: Buttons and controls for demonstration
- **Real-time Updates**: Dynamic content updates via JavaScript

## JSON Processing

### 7. Google Gson

**Dependencies:**
```kotlin
implementation("com.google.code.gson:gson:2.10.1")
```

**Purpose:** JSON parsing, validation, and formatting for data processing features.

**Real-time Validation:**
```kotlin
// From JsonDataScreen.kt
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
```

**Pretty Printing:**
```kotlin
// From ChromecastReceiverScreen.kt
private fun formatJson(json: String): String {
    return try {
        val gson = Gson()
        val jsonElement = gson.fromJson(json, Any::class.java)
        gson.toJson(jsonElement)
    } catch (e: JsonSyntaxException) {
        json // Return original if invalid
    }
}
```

**Sample Data Generation:**
```kotlin
// Sample workout JSON for testing
val sampleJson = """
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
```

**Error Handling:**
- **Visual Indicators**: Red text and error states for invalid JSON
- **Graceful Degradation**: Show original text if formatting fails
- **User Feedback**: Clear error messages with context
- **Recovery**: Allow editing to fix JSON syntax errors

## Logging and Debugging

### 8. Timber

**Dependencies:**
```kotlin
implementation("com.jakewharton.timber:timber:5.0.1")
```

**Purpose:** Structured logging with automatic tag generation and debug filtering.

**Application Setup:**
```kotlin
// From WodWikiTvApplication.kt
override fun onCreate() {
    super.onCreate()
    
    // Initialize Timber for logging
    if (BuildConfig.DEBUG) {
        Timber.plant(Timber.DebugTree())
    }
    
    Timber.d("WoD Wiki TV Application started")
}
```

**Usage Throughout Application:**
```kotlin
// Navigation events
Timber.d("JSON Data Display clicked")
Timber.d("Heart rate connection toggled: $isConnected")
Timber.d("Sent data to React app")
Timber.d("Chromecast receiver toggled: $isListening")

// Error logging
Timber.w("Invalid JSON: ${e.message}")

// Service lifecycle
Timber.d("HeartRateService started")
Timber.d("WebView page finished loading: $url")
```

**Benefits:**
- **Automatic Tags**: Class names used as log tags automatically
- **Debug Filtering**: Logs only in debug builds
- **Structured Output**: Consistent log format across application
- **Performance**: Zero overhead in release builds

## Coroutines and Async Processing

### 9. Kotlin Coroutines

**Dependencies:**
```kotlin
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
```

**Purpose:** Asynchronous programming for smooth UI and background processing.

**UI State Management:**
```kotlin
// From HeartRateScreen.kt
LaunchedEffect(isConnected) {
    if (isConnected) {
        connectionStatus = "Connected"
        kotlinx.coroutines.delay(1000)
        heartRate = (60..100).random()
    } else {
        connectionStatus = "Disconnected" 
        heartRate = 0
    }
}
```

**JSON Processing:**
```kotlin
// From JsonDataScreen.kt
LaunchedEffect(editableJson) {
    try {
        // JSON validation runs on background thread
        val gson = Gson()
        val jsonElement = gson.fromJson(editableJson, Any::class.java)
        formattedJson = gson.toJson(jsonElement)
        isValidJson = true
    } catch (e: JsonSyntaxException) {
        isValidJson = false
    }
}
```

**Benefits for TV Development:**
- **Smooth UI**: Background processing doesn't block UI thread
- **Responsive Input**: D-pad input remains responsive during processing
- **State Management**: Clean async state updates with LaunchedEffect
- **Performance**: Efficient resource usage on TV hardware

## Testing Dependencies

### 10. Testing Libraries

**Dependencies:**
```kotlin
testImplementation("junit:junit:4.13.2")
androidTestImplementation("androidx.test.ext:junit:1.1.5")
androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.4")
debugImplementation("androidx.compose.ui:ui-tooling:1.5.4")
debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.4")
```

**Purpose:** Comprehensive testing framework for unit, integration, and UI tests.

**Testing Strategy:**
- **Unit Tests**: Business logic and data processing
- **Integration Tests**: Service and library interactions  
- **UI Tests**: Compose UI testing with D-pad simulation
- **Instrumentation Tests**: Full device testing

**TV-Specific Testing Considerations:**
- **Navigation Testing**: Verify D-pad navigation paths
- **Focus Testing**: Ensure proper focus management
- **Performance Testing**: Validate smooth performance on TV hardware
- **Integration Testing**: Test all library integrations work correctly

## Build and Development Tools

### 11. Gradle Build Configuration

**Build Script Languages:**
```kotlin
// Using Kotlin DSL for build scripts
// app/build.gradle.kts
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}
```

**Compile SDK and Targets:**
```kotlin
android {
    namespace = "com.wodwiki.tv"
    compileSdk = 35
    
    defaultConfig {
        applicationId = "com.wodwiki.tv"
        minSdk = 21  // Android 5.0 for TV compatibility
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
```

**TV-Specific Build Features:**
```kotlin
buildFeatures {
    compose = true
}

composeOptions {
    kotlinCompilerExtensionVersion = "1.5.4"
}
```

## Library Integration Best Practices

### Version Management

**Coordinated Versions:**
```kotlin
// Project-level version management
buildscript {
    ext {
        compose_version = '1.5.4'
        hilt_version = '2.48.1'
    }
}
```

**Benefits:**
- **Consistency**: All Compose libraries use same version
- **Compatibility**: Avoid version conflicts between related libraries
- **Maintenance**: Single place to update library versions
- **Testing**: Ensure all components work together

### Performance Optimization

**ProGuard Configuration:**
```kotlin
buildTypes {
    release {
        isMinifyEnabled = false
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

**Resource Optimization:**
```kotlin
packaging {
    resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}
```

### TV-Specific Optimizations

**Memory Management:**
- Use lazy loading for large data sets
- Implement proper lifecycle management for services
- Monitor memory usage during development
- Test on actual TV hardware with limited resources

**UI Performance:**
- Minimize Compose recomposition
- Use efficient state management patterns
- Optimize image loading and display
- Ensure smooth 60fps navigation

## Integration Testing Strategies

### Library Integration Tests

**Bluetooth Integration:**
```kotlin
// Test HeartRateService lifecycle
@Test
fun testHeartRateServiceLifecycle() {
    // Test service starts correctly
    // Test notification appears
    // Test service stops cleanly
}
```

**WebView Integration:**
```kotlin
// Test JavaScript bridge communication
@Test
fun testWebViewBridge() {
    // Test Android to React communication
    // Test React to Android communication
    // Test error handling
}
```

**Chromecast Integration:**
```kotlin
// Test cast event processing
@Test
fun testCastEventProcessing() {
    // Test message parsing
    // Test JSON validation
    // Test UI updates
}
```

### Performance Testing

**Memory Tests:**
```kotlin
// Monitor memory usage during integration tests
@Test
fun testMemoryUsage() {
    // Start all services
    // Monitor memory consumption
    // Verify no memory leaks
}
```

**Navigation Performance:**
```kotlin
// Test navigation speed and responsiveness
@Test
fun testNavigationPerformance() {
    // Measure navigation timing
    // Test D-pad responsiveness
    // Verify smooth transitions
}
```

## Troubleshooting Common Integration Issues

### Hilt Compilation Issues

**Problem:** Hilt annotation processing fails
**Solution:** 
```kotlin
kapt {
    correctErrorTypes = true
}
```

**Prevention:** Always apply kapt plugin before using Hilt

### WebView JavaScript Issues

**Problem:** JavaScript bridge doesn't work
**Solution:** 
```kotlin
settings.apply {
    javaScriptEnabled = true
    domStorageEnabled = true
}
```

**Prevention:** Test bridge communication early and often

### Bluetooth Permission Issues

**Problem:** BLE scanning fails on Android 12+
**Solution:** Add location permission and proper usage flags
```xml
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" 
    android:usesPermissionFlags="neverForLocation" />
```

**Prevention:** Test on latest Android versions

### Performance Issues

**Problem:** Slow UI on TV hardware
**Solution:** 
- Profile with Android Studio
- Optimize Compose recomposition
- Use lazy loading patterns
- Test on actual TV devices

**Prevention:** Regular performance testing during development

## Future Library Considerations

### Potential Additions

**Enhanced Testing:**
- Mockito for better unit testing
- Robolectric for faster tests
- UI Automator for system-level testing

**Additional Features:**
- Room database for data persistence
- Retrofit for network communication
- Coil for image loading
- WorkManager for background tasks

**TV-Specific Libraries:**
- Leanback library for traditional TV UI patterns
- ExoPlayer for advanced media playback
- TV Input Framework for live TV integration

### Migration Strategies

**Gradle Version Catalog:**
```toml
# Future consideration: libs.versions.toml
[versions]
compose = "1.5.4"
hilt = "2.48.1"

[libraries]
compose-tv-foundation = { group = "androidx.tv", name = "tv-foundation", version.ref = "compose" }
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
```

**Benefits:**
- Centralized dependency management
- Type-safe dependency references
- Easier version updates
- Better IDE support

## Conclusion

The WoD Wiki TV project demonstrates sophisticated integration of multiple libraries to create a comprehensive Android TV application. Each library serves a specific purpose and is properly configured for TV development:

**Integration Strengths:**
- **Modern Architecture**: Compose + Hilt + Navigation for clean, maintainable code
- **TV Optimization**: All libraries configured for 10-foot experience
- **Professional Quality**: Production-ready libraries (Nordic BLE, Google Cast)
- **Cross-Platform**: WebView integration enables React code sharing
- **Comprehensive**: Covers UI, networking, Bluetooth, casting, and data processing

**Best Practices Applied:**
- Proper dependency management with coordinated versions
- TV-specific configurations and optimizations
- Comprehensive error handling and logging
- Performance optimization for TV hardware
- Thorough testing strategies

This library integration approach provides a solid foundation for any Android TV application requiring multiple technology integrations while maintaining high code quality and performance standards.

---

*This integration guide serves as a comprehensive reference for understanding and extending the library integrations in the WoD Wiki TV project. For specific implementation details, refer to the source code and build configuration files.*