import * as React from 'react';

import { ExpoContentUriViewProps } from './ExpoContentUri.types';

export default function ExpoContentUriView(props: ExpoContentUriViewProps) {
  return (
    <div>
      <iframe
        style={{ flex: 1 }}
        src={props.url}
        onLoad={() => props.onLoad({ nativeEvent: { url: props.url } })}
      />
    </div>
  );
}
