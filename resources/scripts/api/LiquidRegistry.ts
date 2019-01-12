type LiquidRegistry = {
    liquidStorageSaverId: 1306847136
    EmptyByFull: {}
    FullByEmpty: {}
    Storage: _LiquidStorage
    registerLiquid(key: string, name: string, uiTextures: string[], modelTextures?: string[])
    getLiquidData(key: string): { key: string, name: string, uiTextures: string[], uiCache: {}, modelTextures: string[] } | null
    isExists(key: string): boolean
    getLiquidName(key: string): string | null
    getLiquidUITexture(key: string, width: number, height: number): string[]
    getLiquidUIBitmap(key: string, width: number, height: number): android_graphics_bitmap
    registerItem(liquid: string, empty: _ItemObj, full: _ItemObj)
    getEmptyItem(id: _NumId, data?: number): _ItemObj | null
    getItemLiquid(id: _NumId, data?: number): string | null
    getFullItem(id: _NumId, data: number, liquid: string): _ItemObj | null
}

declare class _LiquidStorage {
    constructor(tileEntity: _TEProto)

    liquidAmounts: {};
    liquidLimits: {};
    tileEntity: {} | null;
    getLiquid_flag: boolean;

    hasDataFor(liquid: string): boolean

    setLimit(liquid: string | null, limit: number)

    getLimit(liquid: string): number

    getAmount(liquid: string): number

    getRelativeAmount(liquid: string): number

    //TODO: Container
    updateUiScale(element: string, liquid: string, container?: {})

    setAmount(liquid: string, amount: number)

    getLiquidStored(): string | null

    isFull(liquid: string): boolean

    isEmpty(liquid: string): boolean

    addLiquid(liquid: string, amount: number, c?: boolean): number

    getLiquid(liquid: string, amount: number, c?: boolean): number

    save(): {}

    read(obj: {})
}