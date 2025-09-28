# 🚀 WoD Wiki TV - Quick Start Guide

**Get up and running in 5 minutes!**

---

## ⚡ Prerequisites Check

Before you start, make sure you have:
- [ ] **Android Studio** (2023.3.1+)
- [ ] **JDK 11+** 
- [ ] **Android SDK API 34/35**
- [ ] **Git**

---

## 🏃‍♂️ Quick Setup (5 minutes)

### 1. Clone & Open
```bash
git clone https://github.com/SergeiGolos/wod-wiki-tv.git
cd wod-wiki-tv
```

Open project in Android Studio → Wait for Gradle sync

### 2. Create Android TV Emulator
**Android Studio → Tools → AVD Manager → Create Virtual Device**
- Category: **TV**
- System Image: **Android TV (API 21+)**
- RAM: **2GB+**

### 3. Build & Run
```bash
# Build the project
./gradlew assembleDebug

# Or click "Run" in Android Studio (▶️)
```

**That's it!** The app should launch on your TV emulator.

---

## 🎮 Test the Features

Once running, use your **D-pad/arrow keys** to navigate:

1. **JSON Data Display** - Edit JSON in real-time
2. **Heart Rate Monitor** - Simulated BLE heart rate data
3. **React WebView** - Embedded React application
4. **Chromecast Receiver** - Simulated cast events

---

## 🔧 Development Commands

```bash
# Clean build
./gradlew clean assembleDebug

# Run tests
./gradlew test

# Install to device
./gradlew installDebug

# Check logs
adb logcat | grep "WodWikiTv"
```

---

## ❓ Quick Troubleshooting

**App not showing in TV launcher?**
→ Check AndroidManifest.xml has `LEANBACK_LAUNCHER` category

**Gradle sync failing?**
→ Ensure Android SDK components are installed

**D-pad not working?**
→ Use arrow keys on keyboard or emulator D-pad controls

---

## 📖 Need More Details?

See [`ARCHITECTURE_AND_BUILD_GUIDE.md`](./ARCHITECTURE_AND_BUILD_GUIDE.md) for:
- Complete architecture overview
- Detailed build instructions
- Testing strategies
- Deployment options
- Troubleshooting guide

---

**Happy Coding! 🚀**