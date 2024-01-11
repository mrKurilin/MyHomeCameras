package ru.mrkurilin.myhomecameras.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CamerasData(
    val cameras: List<Camera>?,
    val room: List<String>?,
)