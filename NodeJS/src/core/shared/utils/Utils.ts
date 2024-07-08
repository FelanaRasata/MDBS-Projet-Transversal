export function isEmpty(value: any): boolean {

    return (
        value === null || // check for null
        value === undefined || // check for undefined
        value === '' || // check for empty string
        (Array.isArray(value) && value.length === 0) || // check for empty array
        (typeof value === 'object' && Object.keys(value).length === 0) // check for empty object
    )

}


/**
 * Sort an array based on its attribute value
 * attrFunction: a function to retrieve the attribute of an item
 * */
export function naturalSort(arr: any[], attrFunc: Function, reverse: boolean = false): Array<any> {

    const collator: Intl.Collator = new Intl.Collator(['en-US', 'fr-FR', 'mg-MG'], {
        numeric: true,
        sensitivity: 'base',
    })

    return arr.sort((a, b): number => reverse ? collator.compare(attrFunc(b), attrFunc(a)) : collator.compare(attrFunc(a), attrFunc(b)))

}


export function splitList(lys: Array<any>, chunkSize: number = 25): Array<any[]> {

    const result: any[] = []

    for (let i: number = 0; i < lys.length; i += chunkSize) result.push(lys.slice(i, i + chunkSize))

    return result

}


export function convertMilliseconds(ms: number): string {

    if (ms < 1000) {

        return `${ms} milliseconds`

    } else if (ms < 60000) {

        const seconds = Math.floor(ms / 1000)
        return `${seconds} seconds`

    } else if (ms < 3600000) {

        const minutes = Math.floor(ms / 60000)
        const seconds = Math.floor((ms % 60000) / 1000)
        return `${minutes} minutes ${seconds} seconds`

    } else if (ms < 86400000) {

        const hours = Math.floor(ms / 3600000)
        const minutes = Math.floor((ms % 3600000) / 60000)
        const seconds = Math.floor(((ms % 3600000) % 60000) / 1000)
        return `${hours} hours ${minutes} minutes ${seconds} seconds`

    } else {

        const days = Math.floor(ms / 86400000)
        const hours = Math.floor((ms % 86400000) / 3600000)
        const minutes = Math.floor(((ms % 86400000) % 3600000) / 60000)
        const seconds = Math.floor((((ms % 86400000) % 3600000) % 60000) / 1000)
        return `${days} days ${hours} hours ${minutes} minutes ${seconds} seconds`

    }

}


export function getOrDefault(obj: Record<string, any>, key: string, defaultValue: any): any {

    return obj.hasOwnProperty(key) && !isEmpty(obj[key]) ? obj[key] : defaultValue

}
