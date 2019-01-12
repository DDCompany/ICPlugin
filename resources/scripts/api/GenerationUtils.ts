
type GenerationUtils = {
    __lockedReal: { id: number, data: number }
    isTerrainBlock(id: _BlockNumId): boolean
    isTransparentBlock(id: _BlockNumId): boolean
    canSeeSky(x: number, y: number, z: number): boolean
    randomXZ(cx: number, cz: number): { x: number, z: number }
    randomCoords(cx: number, cz: number, lowestY: number, highestY: number): { x: number, y: number, z: number }
    findSurface(x: number, y: number, z: number): { x: number, y: number, z: number }
    findHighSurface(x: number, z: number): { x: number, y: number, z: number }
    findLowSurface(x: number, z: number): { x: number, y: number, z: number }
    /**
     * @deprecated
     */
    lockInBlock(id: _BlockNumId, data: number, checkerTile: boolean, checkerMode: boolean)
    /**
     * @deprecated
     */
    setLockedBlock(x: number, y: number, z: number)
    /**
     * @deprecated
     */
    genMinable(x: number, y: number, z: number, params: __GenMinableParams)
    generateOre(x: number, y: number, z: number, id: _BlockNumId, data: number, amount: number, notStoneCheck: boolean)
}

type __GenMinableParams = {
    id: _BlockNumId
    data: number
    checkerTile: number
    checkerMode: number
    ratio: number
    size: number
}