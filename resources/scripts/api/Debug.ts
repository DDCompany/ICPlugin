
type Debug = {
    sysTime(): number
    addParticle(x: number, y: number, z: number, id: number, vx?: number, vy?: number, vz?: number, data?: any)
    message(text: string)
    warning(text: string)
    error(text: string)
    m(some: any)
    bitmap(bitmap: {}, title: string)
}