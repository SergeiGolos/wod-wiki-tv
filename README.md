# wod-wiki-tv

# Gym Timer Android TV App Implementation Plan

This document outlines the implementation plan for building an Android TV application designed as a gym timer with QR-code based user connections and Bluetooth sensor data integration (GPS and heart rate monitoring).

---

## 📌 Overview

The application consists of two main components:

1. **Android TV App**: Displays a gym timer, generates QR codes for session connections, and shows real-time user data (heart rate, GPS).
2. **Mobile Companion App**: Allows users to join a session by scanning QR codes, integrating Bluetooth devices (e.g., heart rate monitors), and streaming GPS and heart rate data back to the TV app.

---

## 🚧 Project Setup and Dependencies

### Android TV App:
- Create a new Android TV project in Android Studio.
- Dependencies:
  - Jetpack Compose TV / Leanback library
  - Kotlin Coroutines & Flow
  - WebSocket Server (Ktor or OkHttp)
  - QR Code generation library (ZXing or ML Kit)

### Mobile Companion App:
- Create a separate Android/iOS project or module.
- Dependencies:
  - Jetpack Compose (recommended) or traditional XML-based UI
  - Kotlin Coroutines & Flow
  - WebSocket Client library (Ktor or OkHttp)
  - Bluetooth APIs for heart rate sensors
  - Location APIs (Fused Location Provider)

---

## ✅ Implementation Steps

### 📺 Android TV Application

#### Step 1: Setup Project and UI
- Initialize Android TV project in Android Studio.
- Develop main timer screen UI using Jetpack Compose TV/Leanback.
- Implement QR code generation and display functionality.

#### Step 2: Timer Logic
- Implement timer functionality (countdown, intervals) in `TimerManager`.
- Reflect timer updates in UI using state management.

#### Step 3: WebSocket Server Setup
- Set up a WebSocket server to receive user data.
- Generate unique session identifiers and tokens for security.

#### Step 4: Real-time User Data Display
- Implement UI components to display connected user data (heart rate, GPS).
- Handle incoming WebSocket messages to update the UI.

---

### 📱 Mobile Companion Application

#### Step 1: Setup Project and UI
- Create a new mobile companion app project.
- Develop QR code scanning screen.
- Develop dashboard UI to show current heart rate and GPS status.

#### Step 2: QR Code Scanning and Session Connection
- Implement QR code scanning to read session details.
- Establish WebSocket connection with the Android TV app.

#### Step 3: Bluetooth Integration
- Implement Bluetooth scanning and pairing functionality.
- Continuously read and parse heart rate data.

#### Step 4: GPS and Bluetooth Data Streaming
- Utilize location services to get GPS data.
- Stream the GPS and heart rate data to Android TV via WebSocket.

---

## 📡 Communication Flow Diagram

```
[Android TV App] <-- WebSocket (GPS, HR data) <-- [Mobile Companion App]
      │
      └── Generates QR code (Session ID, Token)
            │
            └── Scanned by Mobile App
```

---

## 🛡️ Security & Privacy

- Ensure secure WebSocket communication using session tokens and encryption.
- Manage Bluetooth pairing securely and safely.
- Clearly communicate data privacy policies to users.

---

## 🚩 Testing

- **Unit Testing:** Core logic (timer, Bluetooth, WebSocket communication).
- **Integration Testing:** End-to-end functionality with real devices.
- **User Acceptance Testing:** Ensure app usability, UI responsiveness, and reliability.

---

## 🚀 Deployment

- Publish Android TV app to Google Play Store (optimize for TV).
- Publish companion mobile app to respective app stores (Google Play, Apple App Store if applicable).
- Provide user documentation and support resources.

---

## 📈 Future Enhancements

- Historical data visualizations and export options.
- Analytics dashboard for trainers.
- Integration with wearable devices (Wear OS, Apple Watch).
- User statistics and achievement tracking.

---

## 📖 Documentation & Maintenance

- Maintain clear, comprehensive documentation for codebase and APIs.
- Schedule regular app updates and security patches.
- Collect and analyze user feedback for continuous improvement.
