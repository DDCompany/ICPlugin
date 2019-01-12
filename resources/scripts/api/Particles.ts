
type Particles = {
    addParticle(x: number, y: number, z: number, id: number, vx?: number, vy?: number, vz?: number, data?: number)
    addFarParticle(x: number, y: number, z: number, id: number, vx?: number, vy?: number, vz?: number, data?: number)
    line (particle: number, coords1: _Coords, coords2: _Coords, gap: number, vel: number, data?: number)
}