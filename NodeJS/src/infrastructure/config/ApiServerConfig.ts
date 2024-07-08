import * as envVar from 'env-var'


export default class ApiServerConfig {

    public static readonly BASE_URL: string = envVar.get('API_HOST').required().asString()

    public static readonly PORT: number = envVar.get('API_PORT').required().asPortNumber()

    public static readonly SERVER_TIMEOUT: number = envVar.get('API_SERVER_TIMEOUT').required().asIntPositive()

    public static readonly DEV_MODE: boolean = envVar.get('NODE_ENV').required().asString() === 'development'

    public static readonly DATETIME_FORMAT: string = 'YYYY-MM-DD HH:mm:ss'

    public static readonly DATE_FORMAT: string = 'YYYY-MM-DD'

}