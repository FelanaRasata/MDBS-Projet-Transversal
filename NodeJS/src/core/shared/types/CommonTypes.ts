export type Nullable<T> = T | null

export type Optional<T> = T | undefined

export type DeepReadonly<T> = {
    readonly [K in keyof T]: T[K] extends object ? DeepReadonly<T[K]> : T[K];
}

export type ReadOnly<T = any> = T extends any[] ? ReadonlyArray<DeepReadonly<T[number]>> : DeepReadonly<T>
