import { requireNativeView } from 'expo';
import * as React from 'react';

import { ExpoContentUriViewProps } from './ExpoContentUri.types';

const NativeView: React.ComponentType<ExpoContentUriViewProps> =
  requireNativeView('ExpoContentUri');

export default function ExpoContentUriView(props: ExpoContentUriViewProps) {
  return <NativeView {...props} />;
}
