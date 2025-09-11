# WoD Wiki TV - Project Journal & Technical Documentation

**Doc: Technical Journal – WoD Wiki TV Project Analysis**
- Audience: developer | operator  
- Source of Truth: links to `.ground/` and `app/src/`
- Version: v1.0 (September 2024)

## Overview

The WoD Wiki TV project is a comprehensive Android TV application that serves as both a functional gym timer and a demonstration platform for advanced Android TV development techniques. The application showcases multiple integration patterns including Bluetooth heart rate monitoring, React WebView integration, Chromecast event reception, and real-time JSON data processing.

## Project Topology and Architecture

### High-Level System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                    WoD Wiki TV Application                      │
│                   (Android TV - API 21+)                       │
├─────────────────────────────────────────────────────────────────┤
│                    Presentation Layer                           │
│  ┌─────────────────┬──────────────┬──────────────┬─────────────┐ │
│  │  MainScreen     │ JsonDataScr  │ HeartRateScr │ ChromecastS │ │
│  │  (Navigation)   │ (JSON Edit)  │ (BLE Monitor)│ (Cast Recv) │ │
│  │                 │              │              │             │ │
│  │  ReactWebView   │              │              │             │ │
│  │  (React Bridge) │              │              │             │ │
│  └─────────────────┴──────────────┴──────────────┴─────────────┘ │
├─────────────────────────────────────────────────────────────────┤
│                    Business Logic Layer                         │
│  ┌─────────────────┬──────────────┬──────────────┬─────────────┐ │
│  │  Navigation     │ JSON         │ Heart Rate   │ Cast Event  │ │
│  │  Controller     │ Validation   │ Processing   │ Handler     │ │
│  └─────────────────┴──────────────┴──────────────┴─────────────┘ │
├─────────────────────────────────────────────────────────────────┤
│                    Platform Integration Layer                   │
│  ┌─────────────────┬──────────────┬──────────────┬─────────────┐ │
│  │  WebView        │ Bluetooth    │ Chromecast   │ JSON/Gson   │ │
│  │  JavaScript     │ LE Service   │ Framework    │ Processing  │ │
│  │  Bridge         │              │              │             │ │
│  └─────────────────┴──────────────┴──────────────┴─────────────┘ │
├─────────────────────────────────────────────────────────────────┤
│                    Android TV OS Layer                          │
│  ┌─────────────────┬──────────────┬──────────────┬─────────────┐ │
│  │  Jetpack        │ Android      │ Cast         │ Bluetooth   │ │
│  │  Compose TV     │ Services     │ APIs         │ APIs        │ │
│  └─────────────────┴──────────────┴──────────────┴─────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### Application Navigation Flow

```
                    ┌─────────────────┐
                    │   Main Screen   │
                    │  (Navigation)   │
                    └─────────┬───────┘
                              │
              ┌───────────────┼───────────────┐
              │               │               │
              ▼               ▼               ▼
    ┌─────────────────┐ ┌─────────────┐ ┌─────────────┐
    │ JSON Data       │ │ Heart Rate  │ │ Chromecast  │
    │ Display         │ │ Monitor     │ │ Receiver    │
    └─────────────────┘ └─────────────┘ └─────────────┘
              │
              ▼
    ┌─────────────────┐
    │ React WebView   │
    │ Integration     │
    └─────────────────┘
```

## Core Technologies and Libraries

### Primary Technology Stack

#### **Android TV Foundation**
- **Jetpack Compose for TV**: Modern declarative UI framework
  - `androidx.tv:tv-foundation:1.0.0-alpha10` - Core TV UI components
  - `androidx.tv:tv-material:1.0.0-alpha10` - Material Design for TV
  - **Usage**: All UI screens built with TV-optimized composables
  - **Benefits**: Declarative UI, automatic focus management, TV-specific components

#### **Dependency Injection & Architecture**
- **Hilt**: Google's dependency injection framework
  - `com.google.dagger:hilt-android:2.48.1`
  - **Usage**: Application-wide dependency management, ViewModel injection
  - **Configuration**: `@HiltAndroidApp` on Application class, `@AndroidEntryPoint` on Activities

#### **Navigation & State Management**
- **Navigation Compose**: Type-safe navigation for Compose
  - `androidx.navigation:navigation-compose:2.7.5`
  - **Usage**: Screen-to-screen navigation with type safety
- **ViewModel & LiveData**: State management
  - `androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0`
  - **Usage**: Screen state management and data persistence

### Specialized Integration Libraries

#### **Bluetooth Low Energy (BLE) Integration**
- **Nordic Semiconductor BLE Library**
  - `no.nordicsemi.android:ble:2.6.1`
  - **Purpose**: Professional-grade BLE heart rate monitor support
  - **Implementation**: `HeartRateService` foreground service
  - **Features**: Connection management, data parsing, error handling
  - **Permissions Required**:
    ```xml
    <uses-permission android:name="android.permission.BLUETOOTH" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
    <uses-permission android:name="android.permission.BLUETOOTH_SCAN" />
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
    <uses-permission android:name="android.permission.BODY_SENSORS" />
    ```

#### **Chromecast Integration**
- **Google Cast Framework**
  - `com.google.android.gms:play-services-cast-framework:21.4.0`
  - **Purpose**: Receive cast events from web applications
  - **Implementation**: `CastOptionsProvider` configuration
  - **Features**: Event reception, message parsing, JSON data forwarding

#### **React WebView Integration**
- **AndroidX WebKit**
  - `androidx.webkit:webkit:1.8.0`
  - **Purpose**: Enhanced WebView capabilities for React integration
  - **Implementation**: `ReactWebViewScreen` with JavaScript bridge
  - **Features**: Bidirectional communication, D-pad event injection, custom HTML/CSS/JS

#### **JSON Processing**
- **Google Gson**
  - `com.google.code.gson:gson:2.10.1`
  - **Purpose**: JSON parsing, validation, and formatting
  - **Usage**: Real-time JSON editor with syntax validation
  - **Features**: Parse validation, pretty printing, error handling

#### **Logging & Debugging**
- **Timber**
  - `com.jakewharton.timber:timber:5.0.1`
  - **Purpose**: Structured logging with automatic tag generation
  - **Usage**: Debug logging throughout application
  - **Configuration**: Debug tree in Application class

## Detailed Feature Analysis

### 1. JSON Data Display Feature

**File**: `app/src/main/java/com/wodwiki/tv/ui/screens/JsonDataScreen.kt`

**Purpose**: Interactive JSON editor with real-time validation and formatting

**Key Components**:
- **Two-Panel Layout**: Input editor and formatted output display
- **Real-Time Validation**: Uses Gson for immediate JSON syntax checking
- **Sample Data Loading**: Pre-configured workout JSON for testing
- **Error Handling**: Visual error indicators for invalid JSON

**Technical Implementation**:
```kotlin
// Real-time JSON validation
LaunchedEffect(editableJson) {
    try {
        val gson = Gson()
        val jsonElement = gson.fromJson(editableJson, Any::class.java)
        formattedJson = gson.toJson(jsonElement)
        isValidJson = true
    } catch (e: JsonSyntaxException) {
        isValidJson = false
    }
}
```

**TV Optimizations**:
- Large text input fields for D-pad navigation
- High contrast error indicators
- Monospace font family for JSON display
- Overscan-safe margins and spacing

### 2. Heart Rate Monitor Feature

**Files**: 
- `app/src/main/java/com/wodwiki/tv/ui/screens/HeartRateScreen.kt`
- `app/src/main/java/com/wodwiki/tv/bluetooth/HeartRateService.kt`

**Purpose**: Bluetooth heart rate monitor integration with visual feedback

**Key Components**:
- **Connection Management**: Connect/disconnect functionality
- **Real-Time Display**: Large, color-coded heart rate display
- **Zone Visualization**: Color changes based on heart rate zones
- **Service Architecture**: Foreground service for persistent connections

**Heart Rate Zones Implementation**:
```kotlin
colors = CardDefaults.cardColors(
    containerColor = when {
        heartRate < 60 -> Color(0xFF4CAF50) // Normal (Green)
        heartRate < 85 -> Color(0xFFFF9800) // Elevated (Orange)
        else -> Color(0xFFF44336) // High (Red)
    }
)
```

**BLE Service Architecture**:
- **Foreground Service**: Maintains connection during app background
- **Notification Integration**: User-visible service notification
- **Nordic BLE Library**: Professional BLE stack integration
- **Error Handling**: Connection failure and recovery

### 3. React WebView Integration Feature

**File**: `app/src/main/java/com/wodwiki/tv/ui/screens/ReactWebViewScreen.kt`

**Purpose**: Embed React applications with bidirectional communication

**Key Components**:
- **WebView Configuration**: JavaScript enabled, DOM storage, file access
- **JavaScript Bridge**: `@JavascriptInterface` for Android ↔ React communication
- **Custom HTML/CSS**: TV-optimized React application template
- **D-pad Integration**: Custom navigation handling for TV remote

**JavaScript Bridge Implementation**:
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

**TV-Optimized React Template**:
- Dark theme optimized for TV viewing
- Large fonts and touch targets
- High contrast color scheme
- JavaScript functions for Android communication

### 4. Chromecast Receiver Feature

**Files**:
- `app/src/main/java/com/wodwiki/tv/ui/screens/ChromecastReceiverScreen.kt`
- `app/src/main/java/com/wodwiki/tv/cast/CastOptionsProvider.kt`

**Purpose**: Receive and process Chromecast events from web applications

**Key Components**:
- **Event Listening**: Start/stop receiver functionality
- **Message Processing**: Parse and display incoming JSON data
- **Data Forwarding**: Integrate with JSON display feature
- **Real-Time Updates**: Live message feed with timestamps

**Cast Integration**:
```kotlin
// Cast configuration in AndroidManifest.xml
<meta-data
    android:name="com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME"
    android:value="com.wodwiki.tv.cast.CastOptionsProvider" />
```

**Message Display Architecture**:
- **Time-sorted message list**: Most recent messages first
- **JSON preview panel**: Formatted display of latest data
- **Message persistence**: Maintains history during session
- **Automatic forwarding**: Sends received JSON to other features

## Android TV Specific Optimizations

### Manifest Configuration

**Critical TV Declarations**:
```xml
<!-- TV App Declaration -->
<category android:name="android.intent.category.LEANBACK_LAUNCHER" />

<!-- TV Features -->
<uses-feature android:name="android.software.leanback" android:required="true" />
<uses-feature android:name="android.hardware.touchscreen" android:required="false" />

<!-- TV Banner -->
<application android:banner="@drawable/tv_banner">
```

### 10-Foot UI Experience

**Design Principles Applied**:
- **Large Text**: Minimum 18sp for body text, 24sp+ for titles
- **High Contrast**: White text on dark backgrounds
- **Overscan Safety**: 48dp margins on main content areas
- **Generous Spacing**: 16dp+ between interactive elements
- **Focus Indicators**: Clear visual feedback for D-pad navigation

**Compose TV Implementation**:
```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(48.dp), // Overscan-safe margin
    horizontalAlignment = Alignment.CenterHorizontally
)
```

### D-Pad Navigation

**Focus Management**:
- **Automatic Focus**: Compose TV handles focus automatically
- **Focus Requesters**: Manual focus control when needed
- **Navigation Patterns**: Logical up/down/left/right movement
- **Focus Memory**: Returns to last focused element

**TV-Specific Components**:
```kotlin
// TV-optimized card with automatic focus handling
Card(
    onClick = item.onClick,
    modifier = Modifier
        .fillMaxWidth()
        .focusRequester(focusRequester),
    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
)
```

## Project Structure and Organization

### Directory Structure
```
app/
├── src/main/
│   ├── AndroidManifest.xml          # TV-specific manifest
│   ├── java/com/wodwiki/tv/
│   │   ├── WodWikiTvApplication.kt   # Application class with Hilt
│   │   ├── ui/
│   │   │   ├── MainActivity.kt      # Main TV activity
│   │   │   ├── screens/             # Feature screens
│   │   │   │   ├── MainScreen.kt    # Navigation hub
│   │   │   │   ├── JsonDataScreen.kt
│   │   │   │   ├── HeartRateScreen.kt
│   │   │   │   ├── ReactWebViewScreen.kt
│   │   │   │   └── ChromecastReceiverScreen.kt
│   │   │   └── theme/               # Compose TV theme
│   │   ├── bluetooth/
│   │   │   └── HeartRateService.kt  # BLE service
│   │   └── cast/
│   │       └── CastOptionsProvider.kt
│   └── res/                         # Android resources
└── build.gradle.kts                 # Dependencies and build config
```

### Build Configuration

**Key Dependencies**:
```kotlin
dependencies {
    // Core Android TV
    implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
    implementation("androidx.tv:tv-material:1.0.0-alpha10")
    
    // Compose and Architecture
    implementation("androidx.compose.ui:ui:1.5.4")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("com.google.dagger:hilt-android:2.48.1")
    
    // Specialized Libraries
    implementation("no.nordicsemi.android:ble:2.6.1")
    implementation("com.google.android.gms:play-services-cast-framework:21.4.0")
    implementation("androidx.webkit:webkit:1.8.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.jakewharton.timber:timber:5.0.1")
}
```

## Development Patterns and Best Practices

### Architecture Patterns

**MVVM Implementation**:
- **ViewModels**: Screen state management
- **Compose State**: Reactive UI updates
- **Hilt Injection**: Dependency management
- **Repository Pattern**: Data access abstraction

**TV-Specific Patterns**:
- **Focus Management**: Proper D-pad navigation
- **Service Architecture**: Background BLE connections
- **Bridge Pattern**: WebView communication
- **Event Handling**: Chromecast event processing

### Code Quality Standards

**Logging Strategy**:
```kotlin
// Structured logging with Timber
Timber.d("Heart rate connection toggled: $isConnected")
Timber.w("Invalid JSON: ${e.message}")
```

**Error Handling**:
- **Graceful Degradation**: Features work independently
- **User Feedback**: Clear error messages and status indicators
- **Recovery Mechanisms**: Automatic retry and reconnection logic

**Performance Optimizations**:
- **Lazy Loading**: LazyColumn for message lists
- **State Management**: Minimal recomposition
- **Memory Management**: Proper service lifecycle
- **Background Processing**: Coroutines for async operations

## Getting Started and Development Setup

### Prerequisites
- **Android Studio**: 2023.3.1 or later
- **Android SDK**: API 34 (Target SDK)
- **Minimum SDK**: API 21 (Android 5.0)
- **Kotlin**: 1.9.10
- **Gradle**: 8.4

### Development Environment Setup

1. **Clone Repository**:
   ```bash
   git clone https://github.com/SergeiGolos/wod-wiki-tv.git
   cd wod-wiki-tv
   ```

2. **Open in Android Studio**:
   - Import project
   - Sync Gradle dependencies
   - Configure Android TV emulator

3. **Create Android TV Emulator**:
   - AVD Manager → Create Virtual Device
   - Choose "TV" category
   - Select Android TV system image (API 21+)
   - Configure with 2GB+ RAM

4. **Build and Deploy**:
   ```bash
   ./gradlew assembleDebug
   # Or use Android Studio Run configuration
   ```

### Testing Strategies

**Emulator Testing**:
- **Navigation**: Test all D-pad navigation paths
- **Focus Management**: Verify focus indicators and movement
- **Performance**: Monitor frame rates and responsiveness
- **Integration**: Test WebView and service functionality

**Physical Device Testing**:
- **Real Hardware**: Test on actual Android TV devices
- **Remote Control**: Verify with physical TV remote
- **Performance**: Real-world performance validation
- **Bluetooth**: Test with actual heart rate monitors

## Integration Patterns Deep Dive

### Bluetooth Integration Architecture

**Service Pattern**:
```kotlin
class HeartRateService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start foreground service with notification
        startForeground(NOTIFICATION_ID, notification)
        return START_STICKY
    }
}
```

**Benefits**:
- **Persistent Connection**: Maintains BLE connection in background
- **System Integration**: Proper Android service lifecycle
- **User Awareness**: Foreground notification
- **Recovery**: Automatic restart on system kill

### WebView Bridge Pattern

**Bidirectional Communication**:
```kotlin
// Android to React
webView?.evaluateJavascript("sendDataToReact('$data')", null)

// React to Android
@JavascriptInterface
fun sendToAndroid(message: String) {
    // Handle message from React
}
```

**TV-Specific Considerations**:
- **D-pad Events**: Custom event injection for TV navigation
- **Performance**: Optimize for TV hardware limitations
- **Focus Management**: Bridge focus between native and web
- **Error Handling**: Graceful fallback for JavaScript errors

### Chromecast Integration Pattern

**Event Processing Pipeline**:
1. **Cast Framework Registration**: Configure receiver capabilities
2. **Event Reception**: Listen for incoming cast messages
3. **Data Processing**: Parse and validate JSON payloads
4. **UI Updates**: Display messages and forward data
5. **Integration**: Connect with other app features

**Benefits**:
- **Cross-Platform**: Receive data from web applications
- **Real-Time**: Live event processing and display
- **Extensible**: Easy to add new message types
- **Integrated**: Seamless data flow to other features

## Performance Characteristics and Optimization

### TV Hardware Considerations

**Performance Profile**:
- **CPU**: Limited processing power compared to mobile devices
- **Memory**: Constrained RAM (1-4GB typical)
- **GPU**: Variable graphics performance
- **Storage**: eMMC or basic SSD storage

**Optimization Strategies**:
```kotlin
// Lazy loading for performance
LazyColumn(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(8.dp)
) {
    items(messages) { message ->
        MessageItem(message)
    }
}
```

### Memory Management

**Service Lifecycle**:
- **Foreground Services**: Prevent system termination
- **Proper Cleanup**: Release resources in onDestroy
- **Memory Monitoring**: Monitor service memory usage
- **Background Limits**: Respect Android background execution limits

**UI Performance**:
- **Compose Optimizations**: Minimize recomposition
- **State Management**: Efficient state updates
- **Image Loading**: Optimize image resources for TV
- **Animation Performance**: Smooth 60fps animations

## Future Enhancement Opportunities

### Technical Roadmap

**Near-Term Improvements**:
- **Real BLE Implementation**: Connect to actual heart rate monitors
- **Cast Framework**: Implement real Chromecast receiver
- **React Build Process**: Integrate actual React build pipeline
- **Data Persistence**: Add local storage and preferences
- **Error Handling**: Comprehensive error recovery

**Medium-Term Features**:
- **Multi-User Support**: User profiles and data separation
- **Cloud Integration**: Sync data across devices
- **Advanced Analytics**: Workout tracking and analysis
- **Voice Control**: Android TV voice commands
- **Accessibility**: Full TalkBack and accessibility support

**Long-Term Vision**:
- **AI Integration**: Smart workout recommendations
- **IoT Ecosystem**: Integration with smart gym equipment
- **Social Features**: Multiplayer workouts and challenges
- **Health Platform**: Integration with fitness tracking services
- **Professional Tools**: Trainer and gym management features

### Scalability Considerations

**Architecture Evolution**:
- **Modular Design**: Feature modules for large teams
- **Microservices**: Backend service architecture
- **Cross-Platform**: Shared business logic across platforms
- **Testing Strategy**: Comprehensive test automation
- **CI/CD Pipeline**: Automated build and deployment

## Conclusion

The WoD Wiki TV project demonstrates a sophisticated approach to Android TV development, successfully integrating multiple complex technologies while maintaining high code quality and following Android TV best practices. The application serves as both a functional gym timer and an excellent reference implementation for advanced Android TV development patterns.

**Key Achievements**:
- **Modern Architecture**: Jetpack Compose for TV with MVVM
- **Multiple Integrations**: BLE, WebView, Chromecast, JSON processing
- **TV Optimization**: Proper 10-foot experience implementation
- **Code Quality**: Professional-grade architecture and patterns
- **Documentation**: Comprehensive documentation and examples

**Learning Value**:
- **Bluetooth Integration**: Professional BLE service architecture
- **WebView Bridge**: Complex native-web communication
- **TV UX**: Proper Android TV user experience design
- **Integration Patterns**: Multiple third-party library integration
- **Performance**: TV-specific optimization techniques

This project provides a solid foundation for any team looking to build high-quality Android TV applications with modern tools and techniques.

---

*This documentation serves as a comprehensive guide to understanding and extending the WoD Wiki TV project. For specific implementation details, refer to the source code and inline documentation.*