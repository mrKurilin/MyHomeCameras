package ru.mrkurilin.myhomecameras.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CamerasResponse(
    val success: Boolean?,
    val data: CamerasData?,
)