package ru.mrkurilin.myhomecameras.presentation.doorsScreen

import ru.mrkurilin.myhomecameras.presentation.util.model.RoomUiModel

data class DoorsScreenState(
    val updating: Boolean = false,
    val rooms: List<RoomUiModel<DoorUiModel>> = listOf(),
)