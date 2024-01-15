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

        fun DoorEntity.toDoorUiModel(): DoorUiModel {
            return DoorUiModel(
                name = this.name,
                room = this.room,
                id = this.id,
                favorites = this.favorites,
                snapshot = this.snapshot,
            )
        }
    }
}