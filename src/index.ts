import { Platform } from "react-native";

if (Platform.OS !== "android") {
  throw new Error("expo-content-uri is Android-only (content:// URIs are Android-specific).");
}

export { default } from "./ExpoContentUriModule";
export * from "./ExpoContentUri.types";