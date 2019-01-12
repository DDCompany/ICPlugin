declare const __mod__: {};
declare const __name__: string;
declare const __dir__: string;
declare const __config__: Config;

declare function runCustomSource();

declare function importLib(name: string, toImport?: string);

declare function getCoreAPILevel(): number;

declare function runOnMainThread(func: () => void): number;

declare function getMCPEVersion(): { str: string, array: any[], main: number };