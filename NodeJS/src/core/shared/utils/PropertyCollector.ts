import { isEmpty } from '@core/shared/utils/Utils'


export class PropertyCollector<T> {

    private readonly data: { [key: string]: T } = {}


    constructor(initialData: { [key: string]: T } = {}) {

        this.data = { ...initialData }

    }


    get(propertyPath: string, defaultValue: T | null = null): T | null {

        const value: any = this.getPropertyValue(propertyPath)

        return isEmpty(value) ? defaultValue : value

    }


    /**
     * @author Rasatarivony Andriamalala Sitraka
     * Code based on ChatGPT code
     *
     * This function extract any property inside an object using a string
     * e.g. const obj = { a: { b: { c: 0 } } };
     * console.log(getPropertyValue(obj, 'a.b.c')); // Output: 0
     * */
    private getPropertyValue(propertyPath: string): any {

        if (isEmpty(this.data) || isEmpty(propertyPath)) return null

        return propertyPath
            .split('.')
            .reduce((acc: any, key: string) => acc && acc[key], this.data)

    }

}