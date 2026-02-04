import { requireNativeModule } from "expo-modules-core";
import type { CopyToAppStorageOptions, CopyToAppStorageResult } from "./ExpoContentUri.types";

type ExpoContentUriNativeModule = {
  copyToAppStorage(uri: string, options?: CopyToAppStorageOptions): Promise<CopyToAppStorageResult>;
};

export default requireNativeModule<ExpoContentUriNativeModule>("ExpoContentUri");