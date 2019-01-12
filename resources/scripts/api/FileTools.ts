
type FileTools = {
    mntdir: "/mnt",
    root: "/storage/emulated/0/",
    workdir: "games/com.mojang/coreengine/",
    moddir: "games/com.mojang/mods/",
    modpedir: "games/com.mojang/modpe/"
    mkdir(dir: string)
    mkworkdirs()
    getFullPath(path: string): string
    isExists(path: string): boolean
    WriteText(path: string, text: string, add?: boolean)
    ReadText(path: string): string | null
    WriteImage(path: string, bitmap: {})
    ReadImage(path: string): {}
    ReadTextAsset(name: string): string | null
    ReadImageAsset(name: string): {} | null
    ReadBytesAsset(name: string): number | null
    GetListOfDirs(path: string): {}[]
    GetListOfFiles(path: string): {}[]
    ReadKeyValueFile(path: string, specialSeparator?: string): {}[]
    WriteKeyValueFile(path: string, data: string, specialSeparator?: string)
    ReadJSON(path: string): {}
    WriteJSON(path: string, obj: {}, beautify?: boolean)
}