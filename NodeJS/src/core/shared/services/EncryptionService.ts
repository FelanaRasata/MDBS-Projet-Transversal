import crypto, { Cipher, Decipher } from 'crypto'
import { get } from 'env-var'


export default class EncryptionService {

    private static readonly ALGORITHM: string = 'aes-256-cbc'

    private static readonly ENCODING: BufferEncoding = 'hex'

    private static readonly IV_LENGTH: number = 16


    private static get key(): Buffer {

        const secretKey: string = get('API_ENCRYPTION_SECRET_KEY').required().asString()

        return Buffer.from(secretKey)

    }


    public static encrypt(data: string): string {

        const iv: Buffer = crypto.randomBytes(this.IV_LENGTH)
        const cipher: Cipher = crypto.createCipheriv(this.ALGORITHM, this.key, iv)

        return Buffer.concat([cipher.update(data), cipher.final(), iv]).toString(this.ENCODING)

    }


    public static decrypt(data: string): string {

        const binaryData: Buffer = Buffer.from(data, this.ENCODING)
        const iv: Buffer = binaryData.subarray(-this.IV_LENGTH)
        const encryptedData: Buffer = binaryData.subarray(0, binaryData.length - this.IV_LENGTH)
        const decipher: Decipher = crypto.createDecipheriv(this.ALGORITHM, this.key, iv)

        return Buffer.concat([decipher.update(encryptedData), decipher.final()]).toString()

    }

}