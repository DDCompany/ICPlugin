
type Saver = {
    addSavesScope(name: string, func: _SaverLoad, func: _SaverSave)
    registerScopeSaver(name: string, saver: {})
    registerObjectSaver(name: string, saver: {})
    registerObject(obj: {}, saverId: number)
    setObjectIgnored(name: string, ignore: boolean)
}

type _SaverLoad = (obj: object | null) => void
type _SaverSave = () => object