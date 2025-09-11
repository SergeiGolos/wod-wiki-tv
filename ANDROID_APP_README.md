# Android TV Application Implementation

This directory contains the Android TV application implementation for the WoD Wiki TV project.

## Project Structure

```
app/
├── build.gradle.kts          # Application dependencies and build configuration
├── src/main/
│   ├── AndroidManifest.xml   # TV-specific manifest with permissions and declarations
│   ├── java/com/wodwiki/tv/
│   │   ├── WodWikiTvApplication.kt     # Application class with Hilt setup
│   │   ├── ui/
│   │   │   ├── MainActivity.kt        # Main TV activity with Compose
│   │   │   ├── screens/              # Feature screens
│   │   │   │   ├── MainScreen.kt     # Navigation hub
│   │   │   │   ├── JsonDataScreen.kt # JSON data display
│   │   │   │   ├── HeartRateScreen.kt # BLE heart rate monitor
│   │   │   │   ├── ReactWebViewScreen.kt # React integration
│   │   │   │   └── ChromecastReceiverScreen.kt # Chromecast events
│   │   │   └── theme/                # Compose TV theme
│   │   ├── cast/
│   │   │   └── CastOptionsProvider.kt # Chromecast configuration
│   │   └── bluetooth/
│   │       └── HeartRateService.kt   # BLE service for heart rate
│   └── res/                          # Android resources
└── proguard-rules.pro               # ProGuard rules
```

## Features Implemented

### 1. Android TV Foundation
- ✅ Proper Android TV manifest configuration
- ✅ Jetpack Compose for TV UI framework
- ✅ TV-optimized themes and typography (10-foot experience)
- ✅ D-pad navigation support with focus management
- ✅ TV banner and icon resources

### 2. JSON Data Display
- ✅ Interactive JSON editor with validation
- ✅ Real-time formatted JSON display
- ✅ Sample workout data for testing
- ✅ Error handling for invalid JSON

### 3. Heart Rate Monitor Support
- ✅ UI for Bluetooth heart rate monitor connection
- ✅ Simulated heart rate display with color-coded zones
- ✅ Service architecture for persistent BLE connections
- ✅ Required Bluetooth permissions in manifest

### 4. React WebView Integration
- ✅ WebView setup for hosting React applications
- ✅ JavaScript bridge for Android ↔ React communication
- ✅ Sample React HTML with interactive elements
- ✅ D-pad navigation support in WebView

### 5. Chromecast Receiver
- ✅ Chromecast framework integration
- ✅ Simulated event reception from website
- ✅ Real-time message display and logging
- ✅ JSON data forwarding to other components

## Key Technical Decisions

### Architecture
- **MVVM Pattern**: Clean separation of concerns with ViewModels, UI, and data layers
- **Jetpack Compose for TV**: Modern declarative UI framework optimized for TV
- **Hilt Dependency Injection**: Simplified dependency management
- **Foreground Services**: For persistent Bluetooth connections

### TV-Specific Optimizations
- **10-foot UI Design**: Large fonts, high contrast, generous spacing
- **Focus Management**: Proper D-pad navigation with visual focus indicators
- **Overscan Safety**: 48dp margins to prevent UI clipping on TVs
- **Landscape-only Layouts**: Optimized for TV aspect ratios

### Cross-Platform Strategy
- **WebView Bridge**: Enables React code sharing between web and TV
- **JSON Protocol**: Standardized data exchange format
- **Modular Architecture**: Easy to extend and maintain

## Build Requirements

- Android Studio 2023.3.1 or later
- Android SDK 34
- Minimum SDK: API 21 (Android 5.0) for TV compatibility
- Kotlin 1.9.10
- Gradle 8.4

## Dependencies

### Core Android TV
- `androidx.tv:tv-foundation` - TV UI components
- `androidx.tv:tv-material` - Material Design for TV
- `androidx.compose.ui` - Jetpack Compose runtime

### Bluetooth & Sensors
- `no.nordicsemi.android:ble` - BLE heart rate monitoring
- Android permissions for Bluetooth and body sensors

### Chromecast
- `com.google.android.gms:play-services-cast-framework`

### React Integration
- `androidx.webkit:webkit` - Enhanced WebView capabilities

## Getting Started

1. **Clone and Open**: Open the project in Android Studio
2. **Sync Dependencies**: Let Gradle sync all dependencies
3. **Configure Emulator**: Create an Android TV emulator (API 21+)
4. **Build and Run**: Deploy to emulator or physical Android TV device

## Testing on Android TV

### Emulator Setup
1. Open AVD Manager in Android Studio
2. Create new Virtual Device
3. Choose "TV" category
4. Select Android TV system image (API 21+)
5. Configure with adequate RAM (2GB+)

### Physical Device Testing
1. Enable Developer Options on Android TV
2. Turn on USB Debugging
3. Connect via USB or wireless debugging
4. Install APK through Android Studio

## Usage Instructions

### JSON Data Display
1. Navigate to "JSON Data Display" from main menu
2. Edit JSON in left panel or load sample data
3. View formatted output in right panel
4. Invalid JSON will show error indicators

### Heart Rate Monitor
1. Select "Heart Rate Monitor" from main menu
2. Click "Connect" to simulate BLE connection
3. View real-time heart rate with color-coded zones
4. Actual BLE implementation requires physical heart rate monitor

### React WebView
1. Choose "React WebView" from main menu
2. View embedded React application
3. Test bidirectional communication with buttons
4. Reload to restart React app

### Chromecast Receiver
1. Access "Chromecast Receiver" from main menu
2. Click "Start Listening" to simulate event reception
3. View received messages and JSON data
4. Data automatically forwards to JSON display

## Development Notes

### Building Without Android Studio
Since this is being developed in a sandbox environment without Android Studio, the project structure and code are ready but cannot be built here. To continue development:

1. Import project into Android Studio
2. Sync Gradle dependencies
3. Resolve any missing dependencies
4. Test on Android TV emulator
5. Iterate on features and UI

### Known Limitations
- BLE heart rate monitoring is simulated (needs real hardware)
- Chromecast events are mocked (needs integration with actual Cast framework)
- React WebView contains sample HTML (needs real React build)

### Next Steps for Production
1. Implement actual BLE heart rate profile parsing
2. Set up real Chromecast receiver registration
3. Build and bundle React application properly
4. Add comprehensive error handling
5. Implement data persistence
6. Add user preferences and settings
7. Performance optimization for TV hardware
8. Accessibility improvements
9. Comprehensive testing on various TV devices

## Contributing

When extending this application:

1. Follow existing architecture patterns (MVVM)
2. Maintain TV-specific UI guidelines (10-foot experience)
3. Ensure D-pad navigation works properly
4. Test on actual Android TV hardware
5. Add appropriate logging with Timber
6. Update documentation for new features

## License

This project is part of the WoD Wiki TV application suite.