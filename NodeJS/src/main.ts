import './aliases'
import ApplicationServer from '@application/ApplicationServer'


runApplication()


function runApplication(): void {

    const server: ApplicationServer = new ApplicationServer()

    server.run()

}