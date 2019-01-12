type ModAPI = {
    registerAPI(name: string, api: {}, desc?: { name: string, props: {} })
    requireAPI(name: string): {}
    requireGlobal(name: string): any
    requireAPIdoc(name: string): any
    requireAPIPropertyDoc(name: string, prop: any)
    getModByName(name: string): {}
    isModLoaded(name: string): boolean
    addAPICallback(name: string, func: (api: {}) => void)
    addModCallback(name: string, func: (mod: {}) => void)
    getModList(): any[]
    getModPEList(): any[]
    addTexturePack(path: string)
    cloneAPI(api: {}, deep?: boolean): {}
    inheritPrototypes(source: {}, target: {}): {}
    cloneObject(source: {}, deep: boolean, rec: number): {}
    debugCloneObject(source: {}, deep: boolean, rec: number): string | {} | null
}