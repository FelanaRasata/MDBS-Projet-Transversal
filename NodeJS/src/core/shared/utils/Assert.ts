import { Nullable, Optional } from '@core/shared/types/CommonTypes'
import { SwissKnife } from '@core/shared/utils/Utils'


export class Assert {


    public static isTrue(expression: boolean, exception: Error): void {

        if (!expression) throw exception

    }


    public static isFalse(expression: boolean, exception: Error): void {

        if (expression) throw exception

    }


    public static notEmpty<T>(value: Optional<Nullable<T>>, exception: Error): T {

        if (SwissKnife.isEmpty(value)) throw exception

        return value as T

    }

}