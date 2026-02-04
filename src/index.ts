// Reexport the native module. On web, it will be resolved to ExpoContentUriModule.web.ts
// and on native platforms to ExpoContentUriModule.ts
export { default } from './ExpoContentUriModule';
export { default as ExpoContentUriView } from './ExpoContentUriView';
export * from  './ExpoContentUri.types';
