
type ToolAPI = {
    blockData: {}
    toolData: {}
    toolMaterials: {}
    blockMaterials: {}
    needDamagableItemFix: boolean
    LastAttackTime: number
    /**
     * Стандартные материалы:
     * stone - 5
     * wood - 1.5
     * dirt - 1.5
     * plant - 1.5
     * fibre - 1.5
     * cobweb - 5
     * unbreaking - 999999999
     */
    addBlockMaterial(name: string, breakingMultiplier: number)
    /**
     * Стандартные материалы:
     * wood - 1, 60, 2, 2
     * stone - 2, 132, 3, 4
     * iron - 3, 251, 4, 6
     * golden - 1, 33, 2, 12
     * diamond - 4, 1562, 5, 12
     */
    addToolMaterial(name: number, material: _ToolMat)
    registerTool(id: _ItemNumId, toolMaterial: _ToolMatS, blockMaterials: _BlockMatS[], params: _ToolParams)
    registerSword(id: _ItemNumId, toolMaterial: _ToolMatS, params: _ToolParams)
    registerBlockMaterial(id: _BlockNumId, mat: _BlockMatS, level: number)
    registerBlockDiggingLevel(id: _BlockNumId, level: number)
    registerBlockMaterialAsArray(mat: _BlockMatS, blocks: number[])
    refresh()
    getBlockData(id: _BlockNumId): _BlockData
    getBlockMaterial(id: _BlockNumId): { multiplier: number, name: string }
    getBlockDestroyLevel(id: number): number
    //TODO: unknown
    getEnchantExtraData(enchant: unknown): unknown
    fortuneDropModifier(drop: _ItemObj, level: number): _ItemObj
    getDestroyTimeViaTool(block: _FullBlock, tool: _ItemObj, coords: _Coords, ignoreNative?: boolean): number
    getToolData(id: _ItemNumId): _ToolData
    getToolLevel(id: _ItemNumId): number
    getToolLevelViaBlock(item: _ItemNumId, block: _BlockNumId): number
    getCarriedToolData(): _ToolData
    getCarriedToolLevel(): number
    startDestroyHook(coords: _Coords, block: _FullBlock, carried: _ItemObj)
    destroyBlockHook(coords: _Coords, block: _FullBlock, carried: _ItemObj)
    playerAttackHook(attackerPlayer: number, victimEntity: number, carried: _ItemObj)
    resetEngine()
    dropExpOrbs(x: number, y: number, z: number, value: number)
    dropOreExp(coords: _Coords, minVal: number, maxVal: number, modifier: number)
}

type _ToolMat = {
    level?: number,
    durability?: number,
    damage?: number
    efficiency?: number
}

type _ToolParams = {
    brokenId?: _NumId,
    damage?: number,
    isWeapon?: boolean,
    //TODO: unnamed args
    calcDestroyTime?: (a, b, c, d) => number
}

type _BlockData = {
    level: number,
    isNative: boolean,
    material: { multiplier: number, name: string }
}

type _ToolData = {
    isNative: boolean,
    damage: number,
    brokenId: number,
    toolMaterial: _ToolMat
}

type _ToolMatS = string
type _BlockMatS = string