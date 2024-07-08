export type CodeDescription = {
    code: number,
    message: string,
};


export default class Code {

    public static SUCCESS: CodeDescription = {
        code: 200,
        message: 'Success.',
    }

    public static CREATED: CodeDescription = {
        code: 201,
        message: 'Created.',
    }

    public static NO_CONTENT: CodeDescription = {
        code: 204,
        message: 'No content.',
    }

    public static BAD_REQUEST_ERROR: CodeDescription = {
        code: 400,
        message: 'Bad request.',
    }

    public static UNAUTHORIZED_ERROR: CodeDescription = {
        code: 401,
        message: 'Unauthorized error.',
    }

    public static WRONG_CREDENTIALS_ERROR: CodeDescription = {
        code: 402,
        message: 'Wrong Credentials.',
    }

    public static ACCESS_DENIED_ERROR: CodeDescription = {
        code: 403,
        message: 'Access denied.',
    }

    public static ENTITY_NOT_FOUND_ERROR: CodeDescription = {
        code: 1000,
        message: 'Entity not found.',
    }

    public static ENTITY_VALIDATION_ERROR: CodeDescription = {
        code: 1001,
        message: 'Entity validation error.',
    }

    public static USE_CASE_PORT_VALIDATION_ERROR: CodeDescription = {
        code: 1002,
        message: 'Use-case ports validation error.',
    }

    public static VALUE_OBJECT_VALIDATION_ERROR: CodeDescription = {
        code: 1003,
        message: 'Value object validation error.',
    }

    public static ENTITY_ALREADY_EXISTS_ERROR: CodeDescription = {
        code: 1004,
        message: 'Entity already exists.',
    }

    public static SESSION_NOT_FOUND: CodeDescription = {
        code: 4007,
        message: 'Session not found.',
    }

}
