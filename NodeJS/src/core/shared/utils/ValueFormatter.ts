import dayjs from 'dayjs'


type unitName = 'infoUnit' | 'frequency'


export class ValueFormatter {

    private static units: { [key in unitName]: string } = {
        'infoUnit': 'B',
        'frequency': 'Hz',
    }


    public static formatDateFromString(dateString: string, format: string): string {

        if (new Date(dateString).toString() === 'Invalid Date') return ''

        return dayjs(dateString).format(format)

    }


    public static autoConvertBytes(value: number, label: boolean = true): string | number {

        return ValueFormatter.autoConvertValue(value, 1024, ValueFormatter.units['infoUnit'], label)

    }


    public static autoConvertHertz(value: number, label: boolean = true): string | number {

        return ValueFormatter.autoConvertValue(value, 1000, ValueFormatter.units['frequency'], label)

    }


    public static autoConvertValue(value: number, unitValue: number, unit: string, label: boolean = true): string | number {

        if (value === 0) return 0

        const s: string[] = ['', 'K', 'M', 'G', 'T', 'P']

        let e: number = Math.floor(Math.log(value) / Math.log(unitValue))
        let result: string = ((value / Math.pow(unitValue, Math.floor(e))).toFixed(2))

        e = e < 0 ? Math.abs(e) : e

        if (label) return `${result} ${s[e]}${unit}`

        return Number(result)

    }


    public static isFloat(value: number): boolean {

        return !isNaN(value) && value % 1 !== 0

    }


    public static autoRoundNumber(value: number): number {

        return this.isFloat(value) ? Number(value.toFixed(2)) : value

    }

}