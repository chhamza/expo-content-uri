import { NativeModule, requireNativeModule } from 'expo';

import { ExpoContentUriModuleEvents } from './ExpoContentUri.types';

declare class ExpoContentUriModule extends NativeModule<ExpoContentUriModuleEvents> {
  PI: number;
  hello(): string;
  setValueAsync(value: string): Promise<void>;
}

// This call loads the native module object from the JSI.
export default requireNativeModule<ExpoContentUriModule>('ExpoContentUri');
