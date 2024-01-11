package ru.mrkurilin.myhomecameras.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GetCamerasResponse(
    val success: Boolean?,
    val data: CamerasData?,
)