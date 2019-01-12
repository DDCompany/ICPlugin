type TileEntity = {
    resetEngine()
    registerPrototype(id: _BlockNumId, proto: _TEProto)
    getPrototype(id: _BlockNumId): _TEProto | null
    isTileEntityBlock(id: _BlockNumId): boolean
    createTileEntityForPrototype(proto: _TEProto, addToUpdate: boolean): {}
    addTileEntity(x: number, y: number, z: number): _TEProto
    addUpdatableAsTileEntity(updatable: {})
    getTileEntity(x: number, y: number, z: number): _TEProto | null
    destroyTileEntity(tileEniity: {}): true
    destroyTileEntityAtCoords(x: number, y: number, z: number): boolean
    isTileEntityLoaded(tileEntity: {}): boolean
    checkTileEntityForIndex(index: unknown): unknown
    CheckTileEntities()
    DeployDestroyChecker(tileEntity: {})
}

declare class _TEProto {
    remove: boolean;
    isLoaded: boolean;
    __initialized: boolean;
    defaultValues: {};
    /**
     * @type defaultValues
     */
    data: {};
    //TODO: Container & Liq Storage
    container: {};
    liquidStorage: {};

    update()

    created()

    init()

    tick()

    click(id: number, count: number, data: number, coords: _Coords): boolean

    destroyBlock(coords: _Coords, player: number)

    redstone(params: { power: number })

    projectileHit(coords: _CoordsSide, projectile: number)

    destroy(): boolean

    //TODO: UI.Window
    getGuiScreen(): {}

    onItemClick(id: number, count: number, data: number): boolean

    selfDestroy()

    requireMoreLiquid(liquid: string, amount: number)

    save(): {}

    read(obj: {})
}