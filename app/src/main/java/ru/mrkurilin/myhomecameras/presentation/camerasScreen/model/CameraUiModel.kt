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
        fun fromEntity(cameraEntity: CameraEntity): CameraUiModel {
            return CameraUiModel(
                id = cameraEntity.id,
                favorites = cameraEntity.favorites,
                name = cameraEntity.name,
                rec = cameraEntity.rec,
                room = cameraEntity.room,
                snapshot = cameraEntity.snapshot,
            )
        }
    }
}