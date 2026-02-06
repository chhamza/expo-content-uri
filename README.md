# expo-content-uri

Android-only Expo native module that resolves `content://` URIs by copying them into your app’s private storage and returning a usable file path.

This is useful when Android APIs (DocumentPicker, share intents, file managers) return `content://...` URIs but your code or libraries require a real filesystem path.

## Platform support

- ✅ Android
- ❌ iOS
- ❌ Web

## Requirements

Because this is a native module:
- **Expo Go is not supported**
- You must use a **development build** (or a custom dev client) / `eas build`

## Installation (Expo)

```bash
npx expo install expo-content-uri

```

## Usage

```ts
import { ExpoContentUri } from "expo-content-uri";

const result = await ExpoContentUri.copyToAppStorage(contentUri);
console.log(result.filePath);
```