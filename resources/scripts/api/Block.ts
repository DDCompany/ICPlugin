
type Block = {
    TYPE_BASE: "createBlock"
    TYPE_ROTATION: "createBlockWithRotation"
    dropFunctions: _DropFunc[]

    getNumericId(id: _BlockStrId): number | null
    createBlock(id: string, data: _BlockDefData, blockType?: _SpecialType)
    createBlockWithRotation(id: string, data: _BlockDefData, blockType?: _SpecialType)
    isNativeTile(id: _BlockNumId): boolean
    registerDropFunctionForID(id: _BlockNumId, func: _DropFunc, level?: number): true
    registerDropFunction(id: _BlockStrId, func: _DropFunc, level?: number): boolean
    defaultDropFunction(coords: _Coords, id: _BlockNumId, data: number, diggingLevel: number): [number, number, number] | null
    getDropFunction(id: _BlockNumId): [number, number, number][] | null
    setDestroyLevelForID(id: _BlockNumId, level: number)
    setDestroyLevel(id: _BlockStrId, level: number)
    setDestroyTime(id: _BlockStrId, time: number)
    getDestroyTime(id: _BlockNumId): number
    setTempDestroyTime(id: _BlockNumId, time: number)
    setBlockMaterial(id: _BlockStrId, material: _BlockMatS, level: number)
    //TODO: unknown
    setRedstoneTile(id: _BlockStrId, data: unknown, isRedstone: boolean)
    onBlockDestroyed(coords: _Coords, fullTile: _FullBlock)
    getBlockDropViaItem(block: _BlockObj, item: _ItemObj, coords: _Coords): [number, number, number][]
    registerPlaceFunctionForID(id: _BlockNumId, func: _PlaceFunc)
    registerPlaceFunction(id: _BlockStrId, func: _PlaceFunc)
    getPlaceFunc(id: _BlockNumId): _PlaceFunc | null
    setBlockShape(id: _BlockNumId, pos1: _Coords, pos2: _Coords, data?: number)
    setShape(id: _BlockNumId, x1: number, y1: number, z1: number, x2: number, y2: number, z2: number, data?: number)
    createSpecialType(desc: _SpecialType, name: string): _SpecialType
    setPrototype(id: _BlockStrId, proto: _BlockProto)
}

type _DropFunc = (coords: _Coords, id: _BlockNumId, data: number, diggingLevel: number, toolLevel: number) => number[][]
type _PlaceFunc = (coords: _CoordsRel, item: _ItemObj, block: _BlockObj) => void
type _BlockDefData = {
    name: string,
    texture: [string, number][],
    inCreative: boolean
}
type _SpecialType = {
    base?: number,
    opaque?: boolean,
    rendertype?: number,
    renderlayer?: number,
    destroytime?: number,
    redstoneconsumer?: boolean,
    lightopacity?: number,
    lightlevel?: number,
    explosionres?: number,
    color?: number[]
}
type _BlockProto = {
    __validBlockTypes?: { createBlock: boolean, createBlockWithRotation: boolean }
    __define?(a: unknown)
    __describe?(a: unknown)
    init?()
    getVariations?(): _BlockDefData[] | null
    getSpecialType?(): _SpecialType | null
    getCategory?(): number | null
    getEnchant?(): number | null
    getProperties?(): unknown | null
    isStackedByData?(): boolean | null
    isEnchanted?(): boolean | null
    getMaterial?(): _BlockMatS | null
    getDestroyLevel?(): number
    getShape?(): unknown
    //Callbacks
    getDrop?(): _DropFunc | null
    onPlaced?(): _PlaceFunc | null
    onItemUsed?(): unknown
}