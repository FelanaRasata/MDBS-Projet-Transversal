import { Get, HttpCode, JsonController } from 'routing-controllers'
import ApiResponse from '@core/shared/api/ApiResponse'
import { OpenAPI } from 'routing-controllers-openapi'
import Code from '@core/shared/types/Code'


@JsonController()
export default class DefaultController {

    @Get('/health')
    @OpenAPI({ summary: 'Health checker endpoint' })
    @HttpCode(200)
    checkHealth(): ApiResponse<null> {

        return ApiResponse.success({
            overrideMessage: 'Don\'t worry, the server isn\'t on fire. Yet.',
            code: Code.SUCCESS,
        })

    }

}