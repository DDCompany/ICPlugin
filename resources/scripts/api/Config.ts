declare class Config {
    constructor(path: string)
    constructor(file: java_io_file)
    constructor(parent: Config, json: {})

    access(key: string): {}

    checkAndRestore(json: string)

    checkAndRestore(json: {})

    get(key: string): {}

    getBool(key: string): boolean

    getNumber(key: string): number

    getString(key: string): string

    getValue(key: string): j_ConfigValue | null

    getNames(): java_util_arrayList_str

    save()

    set(path: string, value: any): boolean
}

declare class j_ConfigValue {
    constructor(parent: Config, str: string)

    get(): {} | null

    set(value: any)

    toString(): string
}