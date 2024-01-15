package ru.mrkurilin.myhomecameras.presentation.camerasScreen.model

import ru.mrkurilin.myhomecameras.data.local.model.CameraEntity

data class CameraUiModel(
    val id: Int,
    val favorites: Boolean,
    val name: String,
    val rec: Boolean,
    val room: String,
    val snapshot: String,
) {

    companion object{
        fun CameraEntity.toCameraUiModel(): CameraUiModel {
            return CameraUiModel(
                id = this.id,
                favorites = this.favorites,
                name = this.name,
                rec = this.rec,
                room = this.room,
                snapshot = this.snapshot,
            )
        }
    }
}