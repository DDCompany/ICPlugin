
type Threading = {
    formatFatalErrorMessage(error: any, threadName: string, threadPriority: number, formatFunc?: _ErrFormat)
    initThread(name: string, func: () => void, priority: number, isErrorFatal?: boolean, formatFunc?: _ErrFormat)
    getThread(name: string)
}

type _ErrFormat = (error: any, threadPriority: number) => any