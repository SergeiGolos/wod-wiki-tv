# WoD Wiki TV - Complete Architecture & Build Guide

**Version**: 1.0  
**Generated**: 2024-09-28  
**Source of Truth**: `.ground/`, `PROJECT_JOURNAL.md`, `ANDROID_APP_README.md`, `LIBRARY_INTEGRATION_GUIDE.md`  
**Audience**: Developers, DevOps Engineers, Product Managers

---

## 🏗️ Project Architecture Overview

The **WoD Wiki TV** project is a sophisticated Android TV application designed as both a functional gym timer and a demonstration platform for advanced Android TV development techniques. The application showcases modern Android development patterns with a focus on the "10-foot experience" optimized for TV viewing.

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    WoD Wiki TV Application                   │
├─────────────────────────────────────────────────────────────┤
│  UI Layer (Jetpack Compose for TV)                         │
│  ├── MainScreen (Navigation Hub)                           │
│  ├── JsonDataScreen (Real-time JSON Editor)                │
│  ├── HeartRateScreen (BLE Heart Rate Monitor)              │
│  ├── ReactWebViewScreen (React Integration)                │
│  └── ChromecastReceiverScreen (Cast Event Handler)         │
├─────────────────────────────────────────────────────────────┤
│  Service Layer                                              │
│  ├── HeartRateService (Foreground BLE Service)             │
│  └── CastOptionsProvider (Chromecast Configuration)        │
├─────────────────────────────────────────────────────────────┤
│  Integration Layer                                          │
│  ├── Bluetooth Low Energy (Nordic BLE Library)             │
│  ├── WebView with JavaScript Bridge                        │
│  ├── Chromecast Framework Integration                      │
│  └── JSON Processing (Gson)                                │
├─────────────────────────────────────────────────────────────┤
│  Foundation Layer                                           │
│  ├── Android TV Framework (Leanback)                       │
│  ├── Hilt Dependency Injection                             │
│  ├── Kotlin Coroutines & Flow                              │
│  └── Timber Logging                                        │
└─────────────────────────────────────────────────────────────┘
```

### Key Design Principles

1. **TV-First Design**: 10-foot UI experience with D-pad navigation
2. **Modular Architecture**: Clean separation of concerns using MVVM pattern
3. **Modern Android Stack**: Jetpack Compose, Hilt DI, Coroutines
4. **Cross-Platform Integration**: WebView bridge for React code sharing
5. **Real-time Data**: Live heart rate monitoring and JSON editing
6. **Professional BLE**: Nordic Semiconductor library for reliable connections

---

## 📱 Application Features

### 1. JSON Data Display & Editor
- **Purpose**: Real-time JSON editing with validation and formatting
- **Key Components**: 
  - Interactive JSON text editor with syntax validation
  - Live formatted output display
  - Sample workout data for testing
  - Error handling with visual indicators
- **Implementation**: Uses Gson for parsing and validation

### 2. Bluetooth Heart Rate Monitor
- **Purpose**: Connect to BLE heart rate monitors for fitness tracking
- **Key Components**:
  - HeartRateService (foreground service for persistent connections)
  - Color-coded heart rate zones
  - Connection status management
  - Nordic BLE library integration
- **Current State**: UI complete, simulated data (production BLE implementation ready)

### 3. React WebView Integration
- **Purpose**: Host React applications within the Android TV app
- **Key Components**:
  - WebView with JavaScript enabled
  - Bidirectional Android ↔ React communication bridge
  - Sample React HTML with interactive elements
  - D-pad navigation support in WebView
- **Use Case**: Code sharing between web and TV platforms

### 4. Chromecast Receiver
- **Purpose**: Receive and process Chromecast events from web applications
- **Key Components**:
  - Cast framework integration
  - Real-time message processing
  - JSON data forwarding to main editor
  - Event timeline display
- **Current State**: Simulated cast events (production ready for real Cast integration)

---

## 🛠️ Build System & Dependencies

### Build Configuration

**Build System**: Android Gradle with Kotlin DSL  
**Gradle Version**: 8.4  
**Android Gradle Plugin**: 8.1.2  
**Kotlin Version**: 1.9.10  

#### Target Specifications
```kotlin
android {
    compileSdk = 35
    minSdk = 21        // Android 5.0 (TV compatibility)
    targetSdk = 35     // Latest Android features
}
```

### Key Dependencies

#### Core Android TV
```kotlin
// TV-specific UI components
implementation("androidx.tv:tv-foundation:1.0.0-alpha10")
implementation("androidx.tv:tv-material:1.0.0-alpha10")

// Jetpack Compose
implementation("androidx.compose.ui:ui:1.5.4")
implementation("androidx.compose.material3:material3:1.1.2")
```

#### Professional Integrations
```kotlin
// Nordic BLE for heart rate monitors
implementation("no.nordicsemi.android:ble:2.6.1")

// Chromecast framework
implementation("com.google.android.gms:play-services-cast-framework:21.4.0")

// WebView enhancements
implementation("androidx.webkit:webkit:1.8.0")

// Hilt dependency injection
implementation("com.google.dagger:hilt-android:2.48.1")
```

---

## 🚀 Build, Test & Deploy Instructions

### Prerequisites

Before building the project, ensure you have:

1. **Android Studio**: 2023.3.1 (Iguana) or later
2. **Android SDK**: API 34/35 installed
3. **Java Development Kit**: JDK 11 or later
4. **Android TV Emulator**: API 21+ with 2GB+ RAM
5. **Git**: For version control

### 🔧 Development Environment Setup

#### Step 1: Clone Repository
```bash
git clone https://github.com/SergeiGolos/wod-wiki-tv.git
cd wod-wiki-tv
```

#### Step 2: Open in Android Studio
1. Launch Android Studio
2. Select "Open an existing project"
3. Navigate to the cloned `wod-wiki-tv` directory
4. Click "OK" and wait for Gradle sync

#### Step 3: Configure Android TV Emulator
1. Open **AVD Manager** (Tools → AVD Manager)
2. Click **"Create Virtual Device"**
3. Select **"TV"** category
4. Choose **Android TV** system image (API 21 or higher)
5. Configure with **2GB+ RAM** for optimal performance
6. Click **"Finished"**

#### Step 4: Sync Project Dependencies
```bash
# From project root directory
./gradlew build --no-daemon
```

*Note: If `gradlew` is not available, use your system's gradle installation:*
```bash
gradle build --no-daemon
```

### 🏗️ Building the Application

#### Debug Build
```bash
# Build debug APK
./gradlew assembleDebug

# Output location: app/build/outputs/apk/debug/app-debug.apk
```

#### Release Build
```bash
# Build release APK (requires signing configuration)
./gradlew assembleRelease

# Output location: app/build/outputs/apk/release/app-release.apk
```

#### Install and Run
```bash
# Install to connected device/emulator
./gradlew installDebug

# Or use Android Studio Run configuration (Recommended)
# Click "Run" button or Shift+F10
```

### 🧪 Testing Strategy

#### Unit Tests
```bash
# Run all unit tests
./gradlew test

# Run tests with coverage
./gradlew testDebugUnitTestCoverage
```

#### Instrumentation Tests
```bash
# Run instrumentation tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

#### TV-Specific Testing Checklist

**D-Pad Navigation Testing**:
- [ ] All screens navigable with D-pad only
- [ ] Focus indicators clearly visible
- [ ] Proper focus management between elements
- [ ] Back button functionality works correctly

**10-Foot UI Testing**:
- [ ] Text readable from 10 feet away
- [ ] UI elements appropriately sized
- [ ] Overscan-safe margins (48dp minimum)
- [ ] High contrast for TV displays

**Performance Testing**:
- [ ] Smooth navigation between screens
- [ ] No frame drops during animations
- [ ] Proper memory management
- [ ] Quick app startup time

### 🚀 Deployment Options

#### Google Play Store (Recommended)
1. **Prepare Release Build**:
   - Configure signing keys in `app/build.gradle.kts`
   - Test thoroughly on real Android TV hardware
   - Ensure all TV manifest requirements are met

2. **Upload to Play Console**:
   - Create Android TV app listing
   - Upload APK/App Bundle
   - Configure store listing with TV screenshots
   - Submit for review

#### Sideloading for Development
```bash
# Enable Developer Options on Android TV
# Install APK directly
adb install app/build/outputs/apk/debug/app-debug.apk

# Launch application
adb shell am start -n com.wodwiki.tv/.ui.MainActivity
```

---

## 🏢 Project Structure Deep Dive

### Source Code Organization
```
app/src/main/
├── AndroidManifest.xml              # TV-specific manifest configuration
├── java/com/wodwiki/tv/
│   ├── WodWikiTvApplication.kt      # Hilt application class
│   ├── ui/
│   │   ├── MainActivity.kt          # Single activity with Compose
│   │   ├── screens/                 # Feature screens
│   │   │   ├── MainScreen.kt        # Navigation hub & menu
│   │   │   ├── JsonDataScreen.kt    # JSON editor with validation
│   │   │   ├── HeartRateScreen.kt   # BLE heart rate monitoring
│   │   │   ├── ReactWebViewScreen.kt # React integration via WebView
│   │   │   └── ChromecastReceiverScreen.kt # Cast event handling
│   │   └── theme/                   # TV-optimized Material3 theme
│   ├── bluetooth/
│   │   └── HeartRateService.kt      # Foreground service for BLE
│   └── cast/
│       └── CastOptionsProvider.kt   # Chromecast configuration
└── res/                             # Android resources
    ├── drawable/                    # TV banner and icons
    ├── values/                      # Strings, colors, dimensions
    └── xml/                         # Backup and data extraction rules
```

### Critical Android TV Manifest Elements

```xml
<!-- Essential TV declarations -->
<uses-feature android:name="android.software.leanback" android:required="true" />
<uses-feature android:name="android.hardware.touchscreen" android:required="false" />

<!-- TV launcher integration -->
<category android:name="android.intent.category.LEANBACK_LAUNCHER" />

<!-- TV banner (320x180px) -->
<application android:banner="@drawable/tv_banner">
```

---

## 🔧 Development Workflow

### Recommended Development Process

1. **Start Android TV Emulator**
   ```bash
   # List available AVDs
   emulator -list-avds
   
   # Start specific TV emulator
   emulator -avd "Android_TV_API_34"
   ```

2. **Iterative Development**
   ```bash
   # Make code changes
   # Build and install
   ./gradlew installDebug
   
   # Test with D-pad navigation on emulator
   # Verify 10-foot UI experience
   ```

3. **Testing Cycle**
   ```bash
   # Unit tests for business logic
   ./gradlew test
   
   # UI tests for Compose components
   ./gradlew connectedAndroidTest
   
   # Manual testing on real hardware (highly recommended)
   ```

### Debugging Tips

**Logging**: Application uses Timber for structured logging
```kotlin
Timber.d("Debug message")
Timber.e("Error message")
```

**ADB Commands for TV Development**:
```bash
# Check logs
adb logcat | grep "WodWikiTv"

# Monitor focus changes
adb logcat | grep "Focus"

# Check TV-specific system logs
adb logcat | grep "Leanback"
```

---

## 🚨 Troubleshooting Guide

### Common Build Issues

**Problem**: Gradle sync fails with plugin resolution error
```
Plugin [id: 'com.android.application'] was not found
```
**Solution**: Ensure Android SDK and build tools are installed
```bash
# Update SDK components
sdkmanager "build-tools;34.0.0" "platforms;android-34"
```

**Problem**: Hilt compilation errors
**Solution**: Ensure kapt plugin is applied before Hilt
```kotlin
plugins {
    id("kotlin-kapt")        // Must be before Hilt
    id("dagger.hilt.android.plugin")
}
```

### Runtime Issues

**Problem**: App not visible in TV launcher
**Solution**: Verify manifest has correct TV declarations
```xml
<category android:name="android.intent.category.LEANBACK_LAUNCHER" />
<uses-feature android:name="android.software.leanback" android:required="true" />
```

**Problem**: D-pad navigation not working
**Solution**: Ensure focusable elements and proper focus management
```kotlin
Modifier.focusRequester(focusRequester)
```

**Problem**: Bluetooth permissions denied on Android 12+
**Solution**: Add location permission and proper usage flags
```xml
<uses-permission android:name="android.permission.BLUETOOTH_SCAN" 
    android:usesPermissionFlags="neverForLocation" />
```

---

## 🔮 Future Enhancements & Roadmap

### Short-term Improvements
- [ ] Complete BLE heart rate monitor implementation
- [ ] Real Chromecast receiver integration
- [ ] Enhanced React-Android communication
- [ ] Unit test coverage expansion

### Long-term Vision
- [ ] Multi-user session management
- [ ] Historical workout data visualization
- [ ] Integration with fitness wearables
- [ ] Cloud synchronization capabilities
- [ ] Advanced analytics dashboard

---

## 📚 Additional Resources

### Key Documentation Files
- `PROJECT_JOURNAL.md` - Technical implementation details
- `ANDROID_APP_README.md` - Usage instructions and features
- `LIBRARY_INTEGRATION_GUIDE.md` - Detailed library configurations
- `UI_DESIGN_MOCKUP.md` - Design specifications and mockups

### External References
- [Android TV Development Guide](https://developer.android.com/training/tv)
- [Jetpack Compose for TV](https://developer.android.com/jetpack/compose/tv)
- [Nordic BLE Library](https://github.com/NordicSemiconductor/Android-BLE-Library)
- [Chromecast Framework](https://developers.google.com/cast/docs/android_sender/)

---

## 🤝 Contributing

When extending this application:

1. **Follow Architecture Patterns**: Maintain MVVM structure with Hilt DI
2. **TV-First Design**: Ensure 10-foot experience compliance
3. **Test D-pad Navigation**: Verify all UI elements are accessible
4. **Real Hardware Testing**: Test on actual Android TV devices
5. **Documentation**: Update relevant documentation files
6. **Logging**: Use Timber for consistent logging patterns

---

**Generated by**: Scribe Agent  
**Date**: 2024-09-28  
**Version**: 1.0  
**Source Files**: `.ground/`, `PROJECT_JOURNAL.md`, `ANDROID_APP_README.md`, `LIBRARY_INTEGRATION_GUIDE.md`