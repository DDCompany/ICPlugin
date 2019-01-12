
type Armor = {
    registerFuncs(id: _ItemNumId, func: _ArmorFunc)
}

type _ArmorFunc = {
    tick?: (slot: _Slot, inventory: {}, index: number) => void
    hurt?: (a, b, c, f, c, e) => void
}