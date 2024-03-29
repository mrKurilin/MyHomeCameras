package ru.mrkurilin.myhomecameras.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DoorsResponse(
    val data: List<DoorsData>?,
    val success: Boolean?,
)