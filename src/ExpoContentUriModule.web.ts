import { registerWebModule, NativeModule } from 'expo';

import { ExpoContentUriModuleEvents } from './ExpoContentUri.types';

class ExpoContentUriModule extends NativeModule<ExpoContentUriModuleEvents> {
  PI = Math.PI;
  async setValueAsync(value: string): Promise<void> {
    this.emit('onChange', { value });
  }
  hello() {
    return 'Hello world! 👋';
  }
}

export default registerWebModule(ExpoContentUriModule, 'ExpoContentUriModule');
