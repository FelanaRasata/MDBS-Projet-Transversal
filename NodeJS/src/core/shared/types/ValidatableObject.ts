import Code from '@core/shared/types/Code'
import { Optional } from '@core/shared/types/CommonTypes'
import Exception from '@core/shared/types/Exception'
import { ClassValidationDetails, ClassValidator } from '@core/shared/utils/ClassValidator'


export default class ValidatableObject {

    public async validate(skipMissingProperties: boolean = false): Promise<void> {

        const details: Optional<ClassValidationDetails> = await ClassValidator.validate(this, undefined, skipMissingProperties)

        if (details) {

            throw Exception.new({ code: Code.ENTITY_VALIDATION_ERROR, data: details })

        }

    }

}