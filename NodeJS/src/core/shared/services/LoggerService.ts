import { PropertyCollector } from '@core/shared/utils/PropertyCollector'
import ansiColors from 'ansi-colors'
import dayjs from 'dayjs'
import * as util from 'util'
import ApiServerConfig from '@infrastructure/config/ApiServerConfig'


enum ELogLevel {
    info = 'info',
    warn = 'warn',
    error = 'error',
}


export default class Loggeo {

    private static readonly LOG_COLORS: PropertyCollector<any> = new PropertyCollector({
        error: ansiColors.red,
        warn: ansiColors.yellow,
        info: ansiColors.blue,
    })


    public static info(message?: any, ...optionalParams: any[]): void {

        console.log(
            [this.buildTimeLog(ELogLevel.info), this.formatMessage(message)].join(' '),
            ...this.formatMessages(optionalParams),
        )

    }


    public static error(message?: any, ...optionalParams: any[]): void {

        console.error(
            [this.buildTimeLog(ELogLevel.error), this.formatMessage(message, ELogLevel.error)].join(' '),
            ...this.formatMessages(optionalParams, ELogLevel.error),
        )

    }


    public static trace(error: any): void {

        console.error(this.buildTimeLog(ELogLevel.error), this.formatMessage(error.stack, ELogLevel.error))

    }


    public static warn(message?: any, ...optionalParams: any[]): void {

        console.log(
            [this.buildTimeLog(ELogLevel.warn), this.formatMessage(message, ELogLevel.warn)].join(' '),
            ...this.formatMessages(optionalParams, ELogLevel.warn),
        )

    }


    private static colorize(message: any, level?: ELogLevel): string {

        const color = this.LOG_COLORS.get(level || '', ansiColors.whiteBright)

        return color(message)

    }


    private static buildTimeLog(level: ELogLevel): string {

        return this.colorize(`[${dayjs(new Date()).format(ApiServerConfig.DATETIME_FORMAT)}] [${String(level).toUpperCase()}]`, level)

    }


    private static formatMessage(message?: any, level?: ELogLevel): string {

        const formattedMessage: string = typeof message === 'object' ? util.inspect(message, { depth: null }) : String(message)

        return this.colorize(formattedMessage, level)

    }


    private static formatMessages(messages: any[], level?: ELogLevel): string[] {

        return messages.map(message => this.formatMessage(message, level))

    }

}