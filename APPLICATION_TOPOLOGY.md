# WoD Wiki TV - Application Topology and Navigation Guide

**Doc: Application Topology – Complete Navigation and Component Overview**
- Audience: developer | user
- Source of Truth: links to `app/src/main/java/com/wodwiki/tv/`
- Version: v1.0 (September 2024)

## Application Navigation Topology

### Complete Application Flow

```
┌────────────────────────────────────────────────────────────────────────────────┐
│                             WoD Wiki TV Application                            │
│                            (Android TV Main Entry)                            │
└─────────────────────────────┬──────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                           MainActivity                                         │
│                     (Jetpack Compose Entry Point)                             │
│                                                                                │
│  - Sets up Compose theme and navigation                                       │
│  - Configures TV-optimized surface                                            │
│  - Initializes Hilt dependency injection                                      │
└─────────────────────────────┬───────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────────────────────────┐
│                            MainScreen                                          │
│                        (Central Navigation Hub)                               │
│                                                                                │
│  ┌─────────────────────────────────────────────────────────────────────────┐  │
│  │                    Navigation Menu Items                                │  │
│  │                                                                         │  │
│  │  1. JSON Data Display          2. Heart Rate Monitor                   │  │
│  │     - JSON editing and             - Bluetooth heart rate              │  │
│  │       validation                     monitoring                         │  │
│  │     - Real-time formatting         - Connection management              │  │
│  │                                     - Zone visualization                │  │
│  │  3. React WebView              4. Chromecast Receiver                  │  │
│  │     - React app integration        - Cast event reception              │  │
│  │     - JavaScript bridge            - Message processing                │  │
│  │     - Bidirectional comm           - JSON data forwarding              │  │
│  └─────────────────────────────────────────────────────────────────────────┘  │
└─────────────────────┬───────────────┬───────────────┬───────────────┬─────────────┘
                      │               │               │               │
                      ▼               ▼               ▼               ▼
         ┌─────────────────┐ ┌─────────────┐ ┌─────────────┐ ┌─────────────┐
         │  JsonDataScreen │ │HeartRateScr │ │ReactWebView │ │ChromecastRcv│
         │                 │ │             │ │Screen       │ │Screen       │
         └─────────────────┘ └─────────────┘ └─────────────┘ └─────────────┘
```

### Detailed Screen Navigation

#### 1. JSON Data Display Screen Flow

```
MainScreen → JsonDataScreen
│
├── Input Panel (Left Side)
│   ├── Text Editor (JSON Input)
│   ├── Real-time Validation
│   ├── Error Indicators
│   └── Sample Data Button
│
├── Output Panel (Right Side)
│   ├── Formatted JSON Display
│   ├── Syntax Highlighting
│   ├── Scrollable Content
│   └── Error Messages
│
└── Navigation
    └── Back Button → MainScreen
```

#### 2. Heart Rate Monitor Screen Flow

```
MainScreen → HeartRateScreen
│
├── Connection Status Panel
│   ├── Status Indicator (Connected/Disconnected)
│   ├── Connect/Disconnect Button
│   └── Connection Instructions
│
├── Heart Rate Display
│   ├── Large BPM Display (72sp font)
│   ├── Color-coded Zones
│   │   ├── Green: Normal (< 60 BPM)
│   │   ├── Orange: Elevated (60-84 BPM)
│   │   └── Red: High (85+ BPM)
│   └── Real-time Updates
│
├── Service Integration
│   ├── HeartRateService (Background)
│   ├── Foreground Notification
│   └── BLE Connection Management
│
└── Navigation
    └── Back Button → MainScreen
```

#### 3. React WebView Screen Flow

```
MainScreen → ReactWebViewScreen
│
├── WebView Container
│   ├── Custom HTML/CSS/JS Template
│   ├── JavaScript Bridge Interface
│   ├── TV-optimized Styling
│   └── D-pad Navigation Support
│
├── Control Panel
│   ├── Send Data to React Button
│   ├── Reload React App Button
│   └── Status Indicators
│
├── JavaScript Bridge
│   ├── Android → React Communication
│   │   └── evaluateJavascript() calls
│   ├── React → Android Communication
│   │   └── @JavascriptInterface methods
│   └── Bidirectional Data Flow
│
└── Navigation
    └── Back Button → MainScreen
```

#### 4. Chromecast Receiver Screen Flow

```
MainScreen → ChromecastReceiverScreen
│
├── Status Panel
│   ├── Listening Status Indicator
│   ├── Start/Stop Listening Button
│   └── Connection Information
│
├── Message List Panel (Left Side)
│   ├── Time-ordered Message Feed
│   ├── Message Type Indicators
│   ├── Timestamp Display
│   └── Scrollable History
│
├── Data Preview Panel (Right Side)
│   ├── Latest JSON Data Display
│   ├── Formatted JSON Output
│   ├── Syntax Highlighting
│   └── Error Handling
│
├── Cast Integration
│   ├── CastOptionsProvider Configuration
│   ├── Event Reception Pipeline
│   ├── JSON Data Processing
│   └── Automatic Data Forwarding
│
└── Navigation
    └── Back Button → MainScreen
```

## Component Architecture and Data Flow

### Service Layer Architecture

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              Service Layer                                     │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                │
│  ┌─────────────────────┐    ┌─────────────────────┐    ┌─────────────────────┐ │
│  │   HeartRateService  │    │  CastOptionsProvider │    │  WebAppInterface    │ │
│  │                     │    │                     │    │                     │ │
│  │  • Foreground       │    │  • Cast Framework   │    │  • JS Bridge        │ │
│  │    Service          │    │    Configuration    │    │    Methods          │ │
│  │  • BLE Connection   │    │  • Receiver Setup   │    │  • @JavascriptInt.  │ │
│  │  • Nordic Library  │    │  • Event Handling   │    │  • Communication    │ │
│  │  • Notification    │    │  • Message Parsing  │    │    Channel          │ │
│  │  • Lifecycle Mgmt  │    │  • JSON Processing  │    │  • Error Handling   │ │
│  └─────────────────────┘    └─────────────────────┘    └─────────────────────┘ │
│           │                           │                           │            │
│           │ Heart Rate Data           │ Cast Events               │ JS Messages│
│           ▼                           ▼                           ▼            │
├─────────────────────────────────────────────────────────────────────────────────┤
│                            Screen Layer                                        │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                │
│  ┌─────────────────────┐    ┌─────────────────────┐    ┌─────────────────────┐ │
│  │   HeartRateScreen   │    │ ChromecastReceiver  │    │  ReactWebViewScreen │ │
│  │                     │    │      Screen         │    │                     │ │
│  │  • Real-time UI     │    │  • Event Display    │    │  • WebView Host     │ │
│  │  • Zone Colors      │    │  • Message List     │    │  • Bridge Control   │ │
│  │  • Connection UI    │    │  • JSON Preview     │    │  • Communication    │ │
│  │  • Service Control  │    │  • Data Forwarding  │    │  • React App        │ │
│  └─────────────────────┘    └─────────────────────┘    └─────────────────────┘ │
└─────────────────────────────────────────────────────────────────────────────────┘
```

### Data Flow Architecture

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                              Data Flow Diagram                                 │
├─────────────────────────────────────────────────────────────────────────────────┤
│                                                                                │
│  External Sources          Service Layer           UI Layer                    │
│                                                                                │
│  ┌─────────────┐           ┌─────────────┐        ┌─────────────┐              │
│  │ BLE Heart   │──────────▶│ HeartRate   │───────▶│ HeartRate   │              │
│  │ Rate        │  Data     │ Service     │  State │ Screen      │              │
│  │ Monitor     │           │             │        │             │              │
│  └─────────────┘           └─────────────┘        └─────────────┘              │
│                                                                                │
│  ┌─────────────┐           ┌─────────────┐        ┌─────────────┐              │
│  │ Chromecast  │──────────▶│ Cast        │───────▶│ Chromecast  │              │
│  │ Sender      │  Events   │ Framework   │  Data  │ Receiver    │              │
│  │ (Web App)   │           │             │        │ Screen      │              │
│  └─────────────┘           └─────────────┘        └─────────────┘              │
│                                                           │                    │
│                                                           │ JSON Data          │
│                                                           ▼                    │
│  ┌─────────────┐           ┌─────────────┐        ┌─────────────┐              │
│  │ React App   │◀─────────▶│ JavaScript  │◀──────▶│ JsonData    │              │
│  │ (Embedded)  │  Bridge   │ Bridge      │  State │ Screen      │              │
│  │             │  Comms    │ Interface   │        │             │              │
│  └─────────────┘           └─────────────┘        └─────────────┘              │
│                                                                                │
│                            ┌─────────────┐        ┌─────────────┐              │
│                            │ Compose     │◀──────▶│ MainScreen  │              │
│                            │ Navigation  │  Nav   │ (Hub)       │              │
│                            │             │  Events│             │              │
│                            └─────────────┘        └─────────────┘              │
└─────────────────────────────────────────────────────────────────────────────────┘
```

## Android TV Specific Navigation Patterns

### D-Pad Navigation Flow

```
MainScreen D-Pad Navigation:
┌─────────────────────────────────────────────────────────────┐
│                     Focus Movement                          │
│                                                             │
│  ┌─────────────────┐     UP ▲      ┌─────────────────┐     │
│  │ JSON Data       │◀──────┼──────▶│ Heart Rate      │     │
│  │ Display         │       ▼       │ Monitor         │     │
│  │ [FOCUSED]       │     DOWN       │                 │     │
│  └─────────────────┘                └─────────────────┘     │
│           ▲                                   ▲             │
│           │ UP                              UP │             │
│           ▼ DOWN                          DOWN ▼             │
│  ┌─────────────────┐                ┌─────────────────┐     │
│  │ React WebView   │                │ Chromecast      │     │
│  │ Integration     │◀──LEFT/RIGHT──▶│ Receiver        │     │
│  │                 │                │                 │     │
│  └─────────────────┘                └─────────────────┘     │
│                                                             │
│  ENTER/SELECT: Navigate to selected screen                 │
│  BACK: Exit to Android TV launcher                         │
└─────────────────────────────────────────────────────────────┘
```

### Focus Management Strategy

#### Screen Entry Focus
- **hasTVPreferredFocus**: First interactive element gets automatic focus
- **FocusRequester**: Manual focus control for complex layouts
- **Focus Memory**: Return to last focused element when returning to screen

#### Navigation Patterns
- **Grid Layout**: MainScreen uses 2x2 grid for menu items
- **Linear Navigation**: Individual screens use top-to-bottom flow
- **Modal Navigation**: Back button always returns to previous screen
- **Focus Trap**: Prevent focus from escaping intended areas

## Feature Integration Patterns

### JSON Data Integration Flow

```
JSON Feature Integration:
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  Input: User Types JSON                                     │
│    │                                                        │
│    ▼                                                        │
│  Validation: LaunchedEffect + Gson                          │
│    │                                                        │
│    ├── Valid JSON ──▶ Format & Display                     │
│    │                                                        │
│    └── Invalid JSON ──▶ Show Error Indicators              │
│                                                             │
│  Output: Real-time Formatted Display                       │
│    │                                                        │
│    ▼                                                        │
│  Integration: Data forwarded to other features             │
│    │                                                        │
│    ├── Chromecast Receiver ──▶ Displays in preview panel  │
│    │                                                        │
│    └── React WebView ──▶ Sends to embedded React app      │
└─────────────────────────────────────────────────────────────┘
```

### Heart Rate Integration Flow

```
Heart Rate Feature Integration:
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  Connection: User Clicks Connect                           │
│    │                                                        │
│    ▼                                                        │
│  Service: HeartRateService starts                          │
│    │                                                        │
│    ├── Foreground Notification ──▶ User Awareness         │
│    │                                                        │
│    ├── BLE Scanning ──▶ Find Heart Rate Monitors          │
│    │                                                        │
│    └── Data Processing ──▶ Parse Heart Rate Values        │
│                                                             │
│  Display: Real-time UI Updates                             │
│    │                                                        │
│    ├── BPM Value ──▶ Large Text Display                   │
│    │                                                        │
│    ├── Zone Colors ──▶ Green/Orange/Red Background        │
│    │                                                        │
│    └── Status ──▶ Connected/Disconnected Indicator        │
│                                                             │
│  Integration: Data available to other features             │
│    │                                                        │
│    └── React WebView ──▶ Heart rate simulation display    │
└─────────────────────────────────────────────────────────────┘
```

### React WebView Integration Flow

```
React WebView Integration:
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  WebView Setup: Configure JavaScript bridge                │
│    │                                                        │
│    ├── JavaScript Enabled ──▶ DOM manipulation allowed    │
│    │                                                        │
│    ├── addJavascriptInterface ──▶ Android methods exposed │
│    │                                                        │
│    └── Load Custom HTML ──▶ TV-optimized React template   │
│                                                             │
│  Communication: Bidirectional message flow                 │
│    │                                                        │
│    ├── Android → React                                     │
│    │   └── evaluateJavascript() ──▶ Call React functions  │
│    │                                                        │
│    └── React → Android                                     │
│        └── @JavascriptInterface ──▶ Call Android methods  │
│                                                             │
│  Features: React app capabilities                          │
│    │                                                        │
│    ├── Heart Rate Display ──▶ Simulated BPM values       │
│    │                                                        │
│    ├── Data Reception ──▶ Shows messages from Android     │
│    │                                                        │
│    └── Interactive Controls ──▶ Buttons trigger actions   │
└─────────────────────────────────────────────────────────────┘
```

### Chromecast Integration Flow

```
Chromecast Integration:
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  Configuration: CastOptionsProvider setup                  │
│    │                                                        │
│    ├── Receiver Registration ──▶ Android manifest entry   │
│    │                                                        │
│    └── Framework Initialization ──▶ Cast SDK ready        │
│                                                             │
│  Event Reception: Listen for cast events                   │
│    │                                                        │
│    ├── Start Listening ──▶ Enable event reception         │
│    │                                                        │
│    ├── Message Processing ──▶ Parse incoming JSON data    │
│    │                                                        │
│    └── Event Logging ──▶ Store with timestamps           │
│                                                             │
│  Display: Real-time event visualization                    │
│    │                                                        │
│    ├── Message List ──▶ Time-ordered event history       │
│    │                                                        │
│    ├── JSON Preview ──▶ Formatted display of latest data  │
│    │                                                        │
│    └── Status Indicators ──▶ Listening/Not listening     │
│                                                             │
│  Integration: Forward data to other features               │
│    │                                                        │
│    └── JSON Data Screen ──▶ Auto-populate with received data│
└─────────────────────────────────────────────────────────────┘
```

## User Journey and Interaction Patterns

### Primary User Journeys

#### 1. JSON Data Editing Journey
```
Start → MainScreen → Select "JSON Data Display" → 
JsonDataScreen → Edit JSON in left panel → 
View formatted output → Load sample data (optional) → 
Navigate back → MainScreen
```

#### 2. Heart Rate Monitoring Journey
```
Start → MainScreen → Select "Heart Rate Monitor" → 
HeartRateScreen → Click "Connect" → 
View simulated heart rate → Monitor zone colors → 
Click "Disconnect" → Navigate back → MainScreen
```

#### 3. React Integration Journey
```
Start → MainScreen → Select "React WebView" → 
ReactWebViewScreen → Wait for React app load → 
Interact with React components → Send data between Android/React → 
Reload React app (optional) → Navigate back → MainScreen
```

#### 4. Chromecast Reception Journey
```
Start → MainScreen → Select "Chromecast Receiver" → 
ChromecastReceiverScreen → Click "Start Listening" → 
View incoming messages → Check JSON preview → 
Stop listening → Navigate back → MainScreen
```

### Cross-Feature Integration Journey
```
Complete Integration Flow:
┌─────────────────────────────────────────────────────────────┐
│                                                             │
│  1. Start Chromecast Receiver                              │
│     └── Receive JSON workout data from web app             │
│                                                             │
│  2. Navigate to JSON Data Display                          │
│     └── See auto-populated JSON from Chromecast           │
│                                                             │
│  3. Navigate to Heart Rate Monitor                         │
│     └── Connect to simulate heart rate monitoring          │
│                                                             │
│  4. Navigate to React WebView                              │
│     └── Send heart rate data to embedded React app        │
│                                                             │
│  5. Cross-Feature Data Flow                                │
│     └── All features working together with shared data     │
└─────────────────────────────────────────────────────────────┘
```

## Performance and Accessibility Considerations

### TV-Specific Performance Optimizations

#### Memory Management
- **Lazy Loading**: LazyColumn for message lists and large data sets
- **State Management**: Minimal recomposition with remember and mutableStateOf
- **Service Lifecycle**: Proper cleanup in onDestroy methods
- **WebView Optimization**: Hardware acceleration enabled

#### Focus and Navigation Performance
- **Focus Caching**: FocusRequester instances cached with remember
- **Navigation Efficiency**: Direct state changes instead of complex navigation
- **Smooth Transitions**: Proper animation curves for TV viewing distance
- **Input Responsiveness**: Immediate visual feedback for D-pad input

### Accessibility Features

#### Visual Accessibility
- **High Contrast**: White text on dark backgrounds
- **Large Text**: Minimum 18sp for body text, 24sp+ for titles
- **Clear Focus**: Strong visual indicators for focused elements
- **Color Accessibility**: Color-blind friendly color choices

#### Navigation Accessibility
- **Logical Flow**: Predictable D-pad navigation patterns
- **Focus Management**: Proper focus order and containment
- **Back Navigation**: Consistent back button behavior
- **Error Handling**: Clear error messages and recovery paths

## Testing and Quality Assurance

### Navigation Testing Checklist

#### D-Pad Navigation Tests
- [ ] All screens reachable from MainScreen
- [ ] Back button returns to previous screen from all screens
- [ ] Focus moves logically in all directions (up/down/left/right)
- [ ] No focus traps or unreachable elements
- [ ] Focus indicators visible and clear on all interactive elements

#### Feature Integration Tests
- [ ] JSON validation works correctly with valid and invalid input
- [ ] Heart rate service starts and stops correctly
- [ ] WebView loads and JavaScript bridge functions work
- [ ] Chromecast receiver starts/stops and processes messages
- [ ] Data flows correctly between integrated features

#### Performance Tests
- [ ] Smooth navigation between screens (60fps)
- [ ] Responsive D-pad input (< 100ms response time)
- [ ] Memory usage stays within reasonable bounds
- [ ] Background services don't impact UI performance
- [ ] WebView performance acceptable on TV hardware

### Device Compatibility Testing

#### Emulator Testing
- **Android TV Emulator**: Test core functionality and navigation
- **Various Screen Sizes**: 720p, 1080p, 4K display testing
- **Performance Simulation**: Test on low-spec emulator configurations

#### Physical Device Testing
- **Popular TV Devices**: Fire TV, Android TV boxes, Smart TVs
- **Remote Controls**: Various remote control types and layouts
- **Real Hardware**: Actual Bluetooth devices and network conditions
- **Performance Validation**: Real-world performance on target hardware

## Troubleshooting and Common Issues

### Navigation Issues

#### Focus Problems
- **Issue**: Focus gets stuck or jumps unexpectedly
- **Solution**: Check FocusRequester usage and focus order
- **Prevention**: Test navigation paths thoroughly with D-pad

#### Back Navigation
- **Issue**: Back button doesn't work correctly
- **Solution**: Verify navigation state management
- **Prevention**: Use consistent navigation patterns

### Integration Issues

#### Bluetooth Connection
- **Issue**: HeartRateService fails to start
- **Solution**: Check permissions and service configuration
- **Prevention**: Proper error handling and user feedback

#### WebView Communication
- **Issue**: JavaScript bridge doesn't work
- **Solution**: Verify @JavascriptInterface annotations
- **Prevention**: Test bridge communication thoroughly

#### Chromecast Reception
- **Issue**: Cast events not received
- **Solution**: Check CastOptionsProvider configuration
- **Prevention**: Validate cast framework setup

### Performance Issues

#### Slow UI Response
- **Issue**: D-pad input feels sluggish
- **Solution**: Optimize state management and recomposition
- **Prevention**: Profile with Android Studio performance tools

#### Memory Usage
- **Issue**: App consumes too much memory
- **Solution**: Review service lifecycle and object retention
- **Prevention**: Regular memory profiling during development

## Conclusion

The WoD Wiki TV application demonstrates a comprehensive Android TV development approach with sophisticated navigation patterns, multiple technology integrations, and proper TV-specific optimizations. The application topology provides clear separation of concerns while enabling seamless data flow between features.

**Key Navigation Strengths**:
- **Intuitive Layout**: Clear 2x2 grid navigation from central hub
- **Consistent Patterns**: All screens follow similar navigation principles
- **Integration Flow**: Features work together with shared data
- **TV Optimization**: Proper 10-foot experience throughout

**Architecture Benefits**:
- **Modular Design**: Each feature is self-contained yet integrated
- **Service Architecture**: Background services for persistent functionality
- **Bridge Patterns**: Clean communication between different technologies
- **Performance Focus**: Optimized for TV hardware constraints

This topology serves as an excellent reference for building complex Android TV applications that integrate multiple technologies while maintaining excellent user experience and code quality.

---

*This navigation guide provides comprehensive coverage of the application structure and user flow patterns. For implementation details, refer to the source code and PROJECT_JOURNAL.md.*