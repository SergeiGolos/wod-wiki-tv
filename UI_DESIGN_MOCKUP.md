# WoD Wiki TV - Android Application UI Design

Since we cannot run the Android application in this sandbox environment, here's a visual description of the implemented UI:

## Main Screen Layout
```
┌─────────────────────────────────────────────────────────────────┐
│                        WoD Wiki TV                              │
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                 JSON Data Display                         │  │
│  │  Display and interact with JSON domain model data        │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                Heart Rate Monitor                         │  │
│  │  Connect to Bluetooth heart rate monitor                 │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │                 React WebView                             │  │
│  │  Host React application for shared code                  │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                 │
│  ┌───────────────────────────────────────────────────────────┐  │
│  │              Chromecast Receiver                          │  │
│  │  Receive Chromecast events from website                  │  │
│  └───────────────────────────────────────────────────────────┘  │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

## JSON Data Display Screen
```
┌─────────────────────────────────────────────────────────────────┐
│  JSON Data Display                              [Back]          │
│                                                                 │
│  ┌─────────────────────────┐  ┌─────────────────────────────┐   │
│  │      JSON Input         │  │  Formatted JSON Output      │   │
│  │                         │  │                             │   │
│  │  {                      │  │  {                          │   │
│  │    "type": "workout",   │  │    "type": "workout",       │   │
│  │    "name": "HIIT",      │  │    "name": "HIIT Session",  │   │
│  │    "duration": 1800     │  │    "duration": 1800,        │   │
│  │  }                      │  │    "exercises": [...]       │   │
│  │                         │  │  }                          │   │
│  │                         │  │                             │   │
│  └─────────────────────────┘  └─────────────────────────────┘   │
│  [Load Sample Data]                                             │
└─────────────────────────────────────────────────────────────────┘
```

## Heart Rate Monitor Screen
```
┌─────────────────────────────────────────────────────────────────┐
│  Heart Rate Monitor                             [Back]          │
│                                                                 │
│              ┌─────────────────────────────────┐                │
│              │         Connected               │                │
│              │        [Disconnect]             │                │
│              └─────────────────────────────────┘                │
│                                                                 │
│                    ┌─────────────────┐                         │
│                    │                 │                         │
│                    │       78        │                         │
│                    │      BPM        │                         │
│                    │                 │                         │
│                    └─────────────────┘                         │
│                                                                 │
│              ┌─────────────────────────────────┐                │
│              │        Instructions:            │                │
│              │  • Enable Bluetooth on TV      │                │
│              │  • Put heart rate monitor in   │                │
│              │    pairing mode                 │                │
│              │  • Click Connect to scan        │                │
│              └─────────────────────────────────┘                │
└─────────────────────────────────────────────────────────────────┘
```

## React WebView Screen
```
┌─────────────────────────────────────────────────────────────────┐
│  React WebView                                  [Back]          │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                 WoD Wiki TV - React Integration            │ │
│  │                                                             │ │
│  │  Connection Status: Ready                                   │ │
│  │                                                             │ │
│  │                    78 BPM                                   │ │
│  │                                                             │ │
│  │  [Send Data to Android] [Simulate Heart Rate] [Clear]      │ │
│  │                                                             │ │
│  │  Received Data:                                             │ │
│  │  Hello from Android TV!                                     │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                                                                 │
│  [Send Data to React]          [Reload React App]              │
└─────────────────────────────────────────────────────────────────┘
```

## Chromecast Receiver Screen
```
┌─────────────────────────────────────────────────────────────────┐
│  Chromecast Receiver                            [Back]          │
│                                                                 │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │  Receiver Status: Listening for events   [Stop Listening]  │ │
│  └─────────────────────────────────────────────────────────────┘ │
│                                                                 │
│  ┌─────────────────────────┐  ┌─────────────────────────────┐   │
│  │  Received Messages (2)  │  │   Latest Data Preview       │   │
│  │                         │  │                             │   │
│  │  ┌─────────────────────┐ │  │  {                          │   │
│  │  │ workout_data 14:30  │ │  │    "action": "start_workout"│   │
│  │  │ {"action": "start"} │ │  │    "workout": {             │   │
│  │  └─────────────────────┘ │  │      "name": "Morning HIIT"│   │
│  │                         │  │      "duration": 1200       │   │
│  │  ┌─────────────────────┐ │  │    }                        │   │
│  │  │ user_data   14:28   │ │  │  }                          │   │
│  │  │ {"user": "john"}    │ │  │                             │   │
│  │  └─────────────────────┘ │  │                             │   │
│  └─────────────────────────┘  └─────────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

## Design Principles Applied

### 10-Foot Experience
- Large, readable fonts (24sp minimum for body text)
- High contrast colors (white text on dark background)
- Generous spacing and padding
- Overscan-safe margins (48dp from edges)

### D-Pad Navigation
- Clear focus indicators on interactive elements
- Logical navigation flow (up/down/left/right)
- Focus remembering when returning to screens
- No touch-specific UI elements

### TV-Optimized Layout
- Landscape-only orientation
- Grid-based layouts for content browsing
- Minimal information density
- Large touch targets (even though no touch input)

### Material Design for TV
- Card-based content organization
- Elevation and shadows for depth
- Consistent color palette
- Smooth animations and transitions

This UI implementation provides all the core functionality described in the issue while following Android TV design guidelines for the best user experience.