import ApiResponseDefaultMessage from '@core/shared/api/ApiResponseDefaultMessage'
import { Nullable, Optional } from '@core/shared/types/CommonTypes'
import { CreateExceptionPayload } from '@core/shared/types/Exception'


export default class ApiResponse<TData> {

    message: string

    data: Nullable<TData>

    status: Optional<number>


    private constructor(message: string, data?: TData, status?: number) {

        this.message = message
        this.data = data || null
        this.status = status

    }


    public static success<TData>({ overrideMessage, code, data }: CreateExceptionPayload<TData>): ApiResponse<TData> {

        const resultMessage: string = overrideMessage || code.message

        return new ApiResponse(resultMessage, data, code.code)

    }


    public static error<TData>({ overrideMessage, code, data }: CreateExceptionPayload<TData>): ApiResponse<TData> {

        const resultMessage: string = overrideMessage || code.message

        return new ApiResponse(resultMessage, data, code.code)

    }

}