package ru.mrkurilin.myhomecameras.presentation.util.model

data class RoomUiModel<T>(
    val name: String,
    val roomItems: List<T>,
)