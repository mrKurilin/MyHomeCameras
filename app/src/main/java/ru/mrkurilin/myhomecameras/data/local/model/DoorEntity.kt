package ru.mrkurilin.myhomecameras.data.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


open class DoorEntity(
    @PrimaryKey
    var id: Int,
    var favorites: Boolean,
    var name: String,
    var room: String,
    var snapshot: String
) : RealmObject {

    @Suppress("unused")
    constructor() : this(
        id = -1,
        favorites = false,
        name = "-1",
        room = "-1",
        snapshot = "-1",
    )
}