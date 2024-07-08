import moduleAlias from 'module-alias'
import { join } from 'path'


moduleAlias.addAliases({
    '@application': join(__dirname, 'application'),
    '@core': join(__dirname, 'core'),
    '@infrastructure': join(__dirname, 'infrastructure'),
})
