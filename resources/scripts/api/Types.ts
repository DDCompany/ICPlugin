type _NumId = number
type _BlockNumId = number
type _ItemNumId = number
type _NumId = _BlockNumId | _ItemNumId
type _BlockStrId = string
type _ItemStrId = string
type _ItemObj = {
    id: number,
    data: number,
    count: number
}
type _BlockObj = {
    id: number,
    data: number
}
type _FullBlock = { id: _BlockNumId, data: number }
type _Coords = { x: number, y: number, z: number }
type _CoordsSide = { x: number, y: number, z: number, side: number }
type _CoordsRel = { x: number, y: number, z: number, relative: _Coords }

//Android
type java_io_file = {}
type java_util_arrayList_str = {}
type android_graphics_bitmap = {}