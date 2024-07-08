import { Optional } from '@core/shared/types/CommonTypes'


export default class Entity {

    constructor(id: Optional<string | number>, createdAt: Optional<number>, updatedAt: Optional<number>, deletedAt: Optional<number>) {

        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
        this.deletedAt = deletedAt

    }


    private _id?: Optional<string | number>


    get id(): Optional<string | number> {

        return this._id

    }


    private set id(value: Optional<string | number>) {

        this._id = value

    }


    private _createdAt?: Optional<number>


    get createdAt(): Optional<number> {

        return this._createdAt

    }


    private set createdAt(value: Optional<number>) {

        if (value) this._createdAt = value
        else this._createdAt = Date.now()

    }


    private _updatedAt?: Optional<number>


    get updatedAt(): Optional<number> {

        return this._updatedAt

    }


    private set updatedAt(value: Optional<number>) {

        if (value) this._updatedAt = value
        else this._updatedAt = Date.now()

    }


    private _deletedAt?: Optional<number>


    get deletedAt(): Optional<number> {

        return this._deletedAt

    }


    private set deletedAt(value: Optional<number>) {

        this._deletedAt = value

    }

}