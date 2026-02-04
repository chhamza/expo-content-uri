export type CopyToAppStorageOptions = {
  subdir?: string;   // default "imports"
  fileName?: string; // optional override name
};

export type CopyToAppStorageResult = {
  filePath: string; // absolute path in app storage
  fileName: string; // written name (deduped)
  size: number;     // bytes written
};