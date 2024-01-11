package ru.mrkurilin.myhomecameras.presentation.doorsScreen

import ru.mrkurilin.myhomecameras.data.local.model.DoorEntity

data class DoorUiModel(
    val name: String,
    val room: String,
    val id: Int,
    val favorites: Boolean,
    val snapshot: String,
) {

    companion object {

        fun fromDoorEntity(doorEntity: DoorEntity): DoorUiModel {
            return DoorUiModel(
                name = doorEntity.name,
                room = doorEntity.room,
                id = doorEntity.id,
                favorites = doorEntity.favorites,
                snapshot = doorEntity.snapshot,
            )
        }
    }
}