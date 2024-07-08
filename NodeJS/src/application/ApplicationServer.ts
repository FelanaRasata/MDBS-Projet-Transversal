import ApiServerConfig from '@infrastructure/config/ApiServerConfig'
import ansiColors from 'ansi-colors'
import bodyParser from 'body-parser'
import { validationMetadatasToSchemas } from 'class-validator-jsonschema'
import compression from 'compression'
import timeout from 'connect-timeout'
import cors, { CorsOptions } from 'cors'
import dayjs from 'dayjs'
import express, { Express, Response } from 'express'
import helmet from 'helmet'
import { createServer, Server as HttpServer } from 'http'
import morgan from 'morgan'
import { join } from 'path'
import 'reflect-metadata'
import {
    getMetadataArgsStorage,
    MetadataArgsStorage,
    RoutingControllersOptions,
    useExpressServer,
} from 'routing-controllers'
import { routingControllersToSpec } from 'routing-controllers-openapi'
import swaggerUi from 'swagger-ui-express'
import Loggeo from '@core/shared/services/LoggerService'
import DefaultController from '@application/http/controllers/DefaultController'


export default class ApplicationServer {

    private readonly port!: number

    private readonly app: Express

    private readonly server!: HttpServer


    constructor() {

        this.app = express()
        this.port = ApiServerConfig.PORT
        this.server = createServer(this.app)
        this.server.timeout = ApiServerConfig.SERVER_TIMEOUT

    }


    setUpControllers(): void {

        const controllers: Function[] = [
            DefaultController,
        ]

        const routingControllersOptions: RoutingControllersOptions = {
            controllers,
            cors: true,
            defaultErrorHandler: false,
            routePrefix: '/api',
        }

        useExpressServer(this.app, routingControllersOptions)

        // Parse class-validator classes into JSON Schema:
        const { defaultMetadataStorage } = require('class-transformer/cjs/storage')
        const schemas: Record<string, any> = validationMetadatasToSchemas({
            classTransformerMetadataStorage: defaultMetadataStorage,
            refPointerPrefix: '#/components/schemas/',
        })

        // Parse routing-controllers classes into OpenAPI spec:
        const storage: MetadataArgsStorage = getMetadataArgsStorage()

        // Setting up API documentation
        const swaggerSpec: Object = routingControllersToSpec(storage, routingControllersOptions, {
            components: {
                schemas,
            },
            info: {
                title: 'TroqueTroc',
                openapi: '3.0.0',
                version: '1.0.0',
                description: '',
            },
            servers: [
                {
                    'url': `${ApiServerConfig.BASE_URL}`,
                    'description': '',
                },
            ],
        })

        // Serve API documentation using swagger-ui-express
        this.app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec))

        // Endpoint to download Swagger JSON for Postman collection
        this.app.get('/json-docs', (_: any, response: Response): void => {

            response.setHeader('Content-Type', 'application/json')
            response.setHeader('Content-Disposition', 'attachment; filename=alfred-vmware-vsphere-aio.openapi.json')

            response.send(swaggerSpec)

        })

    }


    setUpPublicFolders(): void {

        this.app.use('/public', express.static(join(__dirname, '../../public')))

        this.app.get('/', (_: any, response: Response): void => {

            const docsUrl: string = `${ApiServerConfig.BASE_URL}/api-docs/`
            const jsonDocsUrl: string = `${ApiServerConfig.BASE_URL}/json-docs`

            response.render('index', { docsUrl, jsonDocsUrl })

        })

    }


    setUpMiddlewares(): void {

        this.app.use(timeout(ApiServerConfig.SERVER_TIMEOUT))
        this.app.use(helmet())
        this.app.use(compression())
        this.app.use(bodyParser.json({
            limit: 1024 ** 2,
        }))
        this.app.use(bodyParser.urlencoded({
            extended: true,
        }))
        this.app.use(morgan(Loggeo.apiLogger))

        // Configure CORS options
        const corsOptions: CorsOptions = {
            origin: '*',
            credentials: true, // Set to true if your requests include credentials (e.g., cookies, authentication headers)
            allowedHeaders: [
                'Content-Type',
                'Content-Disposition',
            ],
        }

        this.app.use(cors(corsOptions))

    }


    setUpViewEngine(): void {

        this.app.set('view engine', 'ejs')
        this.app.set('views', join(__dirname, '../../views'))

    }


    run(): void {

        try {

            this.setUpMiddlewares()
            this.setUpPublicFolders()
            this.setUpControllers()
            this.setUpViewEngine()

            this.server.listen(this.port, async (): Promise<void> => {
                this.log()
            })

        } catch (error) {

            Loggeo.trace(error)
            process.exit(1)

        }

    }


    log(): void {

        const text: string = ansiColors.bold('==========  Tickling the servers awake  =========='.toUpperCase())

        Loggeo.info(ansiColors.greenBright(text))

    }

}