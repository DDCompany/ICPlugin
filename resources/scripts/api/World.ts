
type World = {
    isLoaded: boolean
    setLoaded(value: boolean)
    nativeSetBlock(x: number, y: number, z: number, id: _BlockNumId, data?: number)
    nativeGetBlockID(x: number, y: number, z: number): number
    nativeGetBlockData(x: number, y: number, z: number): number
    setBlock(x: number, y: number, z: number, id: _BlockNumId, data?: number)
    setBlock(x: number, y: number, z: number, fullBlock: _FullBlock)
    getBlock(x: number, y: number, z: number): _BlockObj
    getBlockID(x: number, y: number, z: number): number
    getBlockData(x: number, y: number, z: number): number
    destroyBlock(x: number, y: number, z: number, drop: boolean)
    getLightLevel(x: number, y: number, z: number): number
    isChunkLoaded(x: number, z: number): boolean
    isChunkLoadedAt(x: number, y: number, z: number): boolean
    getTileEntity(x: number, y: number, z: number): _TEProto | null
    addTileEntity(x: number, y: number, z: number): _TEProto
    removeTileEntity(x: number, y: number, z: number): boolean
    //TODO: Container
    getContainer(x: number, y: number, z: number): unknown
    getWorldTime(): number
    setWorldTime(time: number)
    setDayMode(day: boolean)
    setNightMode(night: boolean)
    getWeather(): { rain: number, thunder: number }
    setWeather(weather: { rain: number, thunder: number })
    drop(x: number, y: number, z: number, id: _NumId, count: number, data?: number)
    explode(x: number, y: number, z: number, power: number, someBool?: boolean)
    getBiome(x: number, z: number): number
    getBiomeName(x: number, z: number): string
    getGrassColor(x: number, z: number): number
    setGrassColor(x: number, z: number, color: number)
    getGrassColorRGB(x: number, z: number): { r: number, g: number, b: number }
    setGrassColorRGB(x: number, z: number, color: { r: number, g: number, b: number })
    canSeeSky(x: number, y: number, z: number): boolean
    playSound(x: number, y: number, z: number, name: string, volume?: number, pitch?: number)
    playSoundAtEntity(entity: number, name: string, volume?: number, pitch?: number)
}