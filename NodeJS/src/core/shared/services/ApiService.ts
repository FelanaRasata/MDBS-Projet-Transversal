import Loggeo from '@core/shared/services/LoggerService'
import axios, { AxiosInstance, AxiosResponse } from 'axios'
import { isEmpty } from '@core/shared/utils/Utils'


export default class ApiService {

    private axiosInstance: AxiosInstance


    constructor(baseUrl?: string) {

        const config: any = {
            timout: 1200_000,
        }

        if (!isEmpty(baseUrl)) config.baseURL = baseUrl

        this.axiosInstance = axios.create(config)

    }


    public async get<T extends any>(url: string, config?: any): Promise<T> {

        const response: AxiosResponse = await this.axiosInstance.get<T>(url, config)

        return this.pipeAxiosResponse<T>(response.data)

    }


    public async delete<T extends any>(url: string, config?: any): Promise<T> {

        const response: AxiosResponse = await this.axiosInstance.delete(url, config)

        return this.pipeAxiosResponse<T>(response.data)

    }


    public async post<T extends any>(url: string, data?: any, config?: any): Promise<T> {

        const response: AxiosResponse = await this.axiosInstance.post(url, data, config)

        return this.pipeAxiosResponse<T>(response.data)

    }


    public async put<T extends any>(url: string, data?: any, config?: any): Promise<T> {

        const response: AxiosResponse = await this.axiosInstance.put(url, data, config)

        return this.pipeAxiosResponse<T>(response)

    }


    private pipeAxiosResponse<T extends any>(result: any): Promise<T> {

        const { error, status, message, data } = result

        if (status) {

            if (status !== 200) Loggeo.error(message)

            return data

        } else if (error) {

            if (!isEmpty(error) && error === true) Loggeo.error(message)

            return data

        } else {

            return result

        }

    }

}