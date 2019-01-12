type Game = {
    prevent()
    message(text: string)
    tipMessage(text: string)
    dialogMessage(text: string, title: string)
    setDifficulty(difficulty: number)
    getDifficulty(): number
    getMinecraftVersion(): string
    getEngineVersion(): string
}