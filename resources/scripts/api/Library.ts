declare function LIBRARY(desc: {
    name: string,
    version: number,
    shared: boolean,
    api: "CoreEngine" | "AdaptedScript",
    dependencies?: string[]
});

declare function EXPORT(name: string, some: any);