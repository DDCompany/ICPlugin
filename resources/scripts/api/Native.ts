
const Native = {
    ArmorType: {
        helmet: 0,
        chestplate: 1,
        leggings: 2,
        boots: 3
    },
    ItemCategory: {
        DECORATION: 2,
        FOOD: 4,
        INTERNAL: 0,
        MATERIAL: 1,
        TOOL: 3
    },
    ParticleType: {
        angryVillager: 30,
        bubble: 1,
        carrotboost: "carrotboost",
        cloud: 4,
        crit: 2,
        dripLava: 22,
        dripWater: 21,
        enchantmenttable: 32,
        fallingDust: 23,
        flame: 6,
        happyVillager: 31,
        heart: 15,
        hugeexplosion: 13,
        hugeexplosionSeed: 12,
        ink: 27,
        itemBreak: 10,
        largeexplode: 5,
        lava: 7,
        mobFlame: 14,
        note: 24,
        portal: 18,
        rainSplash: 29,
        redstone: 9,
        slime: 28,
        smoke: 3,
        smoke2: 8,
        snowballpoof: 11,
        spell: 24,
        spell2: 25,
        spell3: 26,
        splash: 19,
        suspendedTown: 17,
        terrain: 16,
        waterWake: 20,
        witchspell: "witchspell"
    },
    Color: {
        AQUA: "§b",
        BEGIN: "§",
        BLACK: "§0",
        BLUE: "§9",
        BOLD: "§l",
        DARK_AQUA: "§3",
        DARK_BLUE: "§1",
        DARK_GRAY: "§8",
        DARK_GREEN: "§2",
        DARK_PURPLE: "§5",
        DARK_RED: "§4",
        GOLD: "§6",
        GRAY: "§7",
        GREEN: "§a",
        LIGHT_PURPLE: "§d",
        RED: "§c",
        RESET: "§r",
        WHITE: "§f",
        YELLOW: "§e"
    },
    EntityType: {
        ARROW: 80,
        BAT: 19,
        BLAZE: 43,
        BOAT: 90,
        CAVE_SPIDER: 40,
        CHICKEN: 10,
        COW: 11,
        CREEPER: 33,
        EGG: 82,
        ENDERMAN: 38,
        EXPERIENCE_ORB: 69,
        EXPERIENCE_POTION: 68,
        FALLING_BLOCK: 66,
        FIREBALL: 85,
        FISHING_HOOK: 77,
        GHAST: 41,
        IRON_GOLEM: 20,
        ITEM: 64,
        LAVA_SLIME: 42,
        LIGHTNING_BOLT: 93,
        MINECART: 84,
        MUSHROOM_COW: 16,
        OCELOT: 22,
        PAINTING: 83,
        PIG: 12,
        PIG_ZOMBIE: 36,
        PLAYER: 63,
        POLAR_BEAR: 28,
        PRIMED_TNT: 65,
        RABBIT: 18,
        SHEEP: 13,
        SILVERFISH: 39,
        SKELETON: 34,
        SLIME: 37,
        SMALL_FIREBALL: 94,
        SNOWBALL: 81,
        SNOW_GOLEM: 21,
        SPIDER: 35,
        SQUID: 17,
        THROWN_POTION: 86,
        VILLAGER: 15,
        WOLF: 14,
        ZOMBIE: 32,
        ZOMBIE_VILLAGER: 44
    },
    MobRenderType: {
        arrow: 25,
        bat: 10,
        blaze: 18,
        boat: 35,
        camera: 48,
        chicken: 5,
        cow: 6,
        creeper: 22,
        egg: 28,
        enderman: 24,
        expPotion: 45,
        experienceOrb: 40,
        fallingTile: 33,
        fireball: 37,
        fishHook: 26,
        ghast: 17,
        human: 3,
        ironGolem: 42,
        item: 4,
        lavaSlime: 16,
        lightningBolt: 41,
        map: 50,
        minecart: 34,
        mushroomCow: 7,
        ocelot: 43,
        painting: 32,
        pig: 8,
        player: 27,
        rabbit: 46,
        sheep: 9,
        silverfish: 21,
        skeleton: 19,
        slime: 23,
        smallFireball: 38,
        snowGolem: 44,
        snowball: 29,
        spider: 20,
        squid: 36,
        thrownPotion: 31,
        tnt: 2,
        unknownItem: 30,
        villager: 12,
        villagerZombie: 39,
        witch: 47,
        wolf: 11,
        zombie: 14,
        zombiePigman: 15
    },
    PotionEffect: {
        absorption: 22,
        blindness: 15,
        confusion: 9,
        damageBoost: 5,
        damageResistance: 11,
        digSlowdown: 4,
        digSpeed: 3,
        fireResistance: 12,
        harm: 7,
        heal: 6,
        healthBoost: 21,
        hunger: 17,
        invisibility: 14,
        jump: 8,
        movementSlowdown: 2,
        movementSpeed: 1,
        nightVision: 16,
        poison: 19,
        regeneration: 10,
        saturation: 23,
        waterBreathing: 13,
        weakness: 18,
        wither: 20
    },
    Dimension: {
        END: 2,
        NETHER: 1,
        NORMAL: 0
    },
    ItemAnimation: {
        bow: 4,
        normal: 0
    },
    BlockSide: {
        DOWN: 0,
        EAST: 5,
        NORTH: 2,
        SOUTH: 3,
        UP: 1,
        WEST: 4
    },
    Enchantment: {
        AQUA_AFFINITY: 7,
        BANE_OF_ARTHROPODS: 11,
        BLAST_PROTECTION: 3,
        DEPTH_STRIDER: 8,
        EFFICIENCY: 15,
        FEATHER_FALLING: 2,
        FIRE_ASPECT: 13,
        FIRE_PROTECTION: 1,
        FLAME: 21,
        FORTUNE: 18,
        INFINITY: 22,
        KNOCKBACK: 12,
        LOOTING: 14,
        LUCK_OF_THE_SEA: 23,
        LURE: 24,
        POWER: 19,
        PROJECTILE_PROTECTION: 4,
        PROTECTION: 0,
        PUNCH: 20,
        RESPIRATION: 6,
        SHARPNESS: 9,
        SILK_TOUCH: 16,
        SMITE: 10,
        THORNS: 5,
        UNBREAKING: 17
    },
    EnchantType: {
        all: 16383,
        axe: 512,
        book: 16383,
        bow: 32,
        fishingRod: 4096,
        flintAndSteel: 256,
        hoe: 64,
        pickaxe: 1024,
        shears: 128,
        shovel: 2048,
        weapon: 16
    },
    BlockRenderLayer: {
        alpha: 4099,
        alpha_seasons: 5,
        alpha_single_side: 4,
        blend: 6,
        doubleside: 2,
        far: 9,
        opaque: 0,
        opaque_seasons: 1,
        seasons_far: 10,
        seasons_far_alpha: 11,
        water: 7
    }
};