package ru.mrkurilin.myhomecameras.presentation.camerasScreen

import ru.mrkurilin.myhomecameras.presentation.camerasScreen.model.CameraUiModel
import ru.mrkurilin.myhomecameras.presentation.util.model.RoomUiModel

data class CamerasUIState(
    val updating: Boolean = false,
    val rooms: List<RoomUiModel<CameraUiModel>> = listOf(),
)