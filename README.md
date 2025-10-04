# WoD Wiki TV - Android TV Fitness Application

[![Android TV](https://img.shields.io/badge/Android%20TV-API%2021+-brightgreen)](https://developer.android.com/tv)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.10-blue)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5.4-orange)](https://developer.android.com/jetpack/compose)

A comprehensive Android TV application showcasing modern development techniques including Bluetooth heart rate monitoring, React WebView integration, Chromecast functionality, and real-time JSON data processing.

## 🚀 Quick Start

**Want to get started immediately?** See [`QUICKSTART_GUIDE.md`](./QUICKSTART_GUIDE.md)

## 📖 Complete Documentation

**Need detailed architecture and build information?** See [`ARCHITECTURE_AND_BUILD_GUIDE.md`](./ARCHITECTURE_AND_BUILD_GUIDE.md)

---

## 🎯 Key Features

- **📱 Android TV Optimized**: Built for 10-foot experience with D-pad navigation
- **💓 Heart Rate Monitoring**: Bluetooth Low Energy integration with Nordic library
- **⚛️ React Integration**: WebView bridge for cross-platform code sharing
- **📺 Chromecast Ready**: Receive and process Cast framework events
- **📝 JSON Editor**: Real-time JSON editing with validation and formatting
- **🏗️ Modern Stack**: Jetpack Compose, Hilt DI, Kotlin Coroutines

## 📊 Project Overview

The WoD Wiki TV project demonstrates a modern Android TV application with advanced integrations:

```
┌─────────────────────────────────────────────────────────────┐
│                    WoD Wiki TV Application                   │
├─────────────────────────────────────────────────────────────┤
│  🎮 TV-Optimized UI (Jetpack Compose for TV)               │
│  ├── JSON Data Editor & Validator                          │
│  ├── Bluetooth Heart Rate Monitor                          │
│  ├── React WebView Integration                              │
│  └── Chromecast Event Receiver                             │
├─────────────────────────────────────────────────────────────┤
│  🔧 Services & Integration                                  │
│  ├── HeartRateService (BLE Foreground Service)             │
│  ├── CastOptionsProvider (Chromecast Config)               │
│  └── JavaScript Bridge (Android ↔ React)                   │
├─────────────────────────────────────────────────────────────┤
│  🏗️ Foundation                                              │
│  ├── Android TV Framework (Leanback)                       │
│  ├── Hilt Dependency Injection                             │
│  ├── Kotlin Coroutines & Flow                              │
│  └── Professional Libraries (Nordic BLE, Cast SDK)         │
└─────────────────────────────────────────────────────────────┘
```

## 📚 Documentation Structure

| Document | Purpose | Audience |
|----------|---------|----------|
| [`QUICKSTART_GUIDE.md`](./QUICKSTART_GUIDE.md) | Get running in 5 minutes | New developers |
| [`ARCHITECTURE_AND_BUILD_GUIDE.md`](./ARCHITECTURE_AND_BUILD_GUIDE.md) | Complete technical guide | All developers |
| [`PROJECT_JOURNAL.md`](./PROJECT_JOURNAL.md) | Implementation details | Technical team |
| [`ANDROID_APP_README.md`](./ANDROID_APP_README.md) | Feature documentation | Product team |
| [`LIBRARY_INTEGRATION_GUIDE.md`](./LIBRARY_INTEGRATION_GUIDE.md) | Dependency details | DevOps/Integration |

## 🏗️ Quick Build Instructions

```bash
# Clone repository
git clone https://github.com/SergeiGolos/wod-wiki-tv.git
cd wod-wiki-tv

# Build debug APK
./gradlew assembleDebug

# Install to Android TV emulator/device
./gradlew installDebug
```

## 🎯 Current Implementation Status

- ✅ **Android TV Foundation**: Complete TV manifest, navigation, theming
- ✅ **JSON Data Display**: Real-time editor with validation
- ✅ **Heart Rate Monitor**: UI complete, simulated data (BLE integration ready)
- ✅ **React WebView**: Bidirectional communication bridge
- ✅ **Chromecast Receiver**: Event processing (simulated, framework integrated)

## 🔧 Technology Stack

| Category | Technology | Version |
|----------|------------|---------|
| **Platform** | Android TV | API 21+ |
| **Language** | Kotlin | 1.9.10 |
| **UI Framework** | Jetpack Compose for TV | 1.5.4 |
| **Architecture** | MVVM + Hilt DI | 2.48.1 |
| **BLE Library** | Nordic Semiconductor | 2.6.1 |
| **Cast Framework** | Google Cast SDK | 21.4.0 |
| **Build System** | Gradle with Kotlin DSL | 8.4 |

## 🤝 Contributing

Contributions are welcome! Please read the contributing guidelines in [`ARCHITECTURE_AND_BUILD_GUIDE.md`](./ARCHITECTURE_AND_BUILD_GUIDE.md#-contributing) before getting started.

## 📄 License

This project is part of the WoD Wiki TV application suite.
