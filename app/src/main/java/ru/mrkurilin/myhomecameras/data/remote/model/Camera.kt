package ru.mrkurilin.myhomecameras.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Camera(
    val favorites: Boolean?,
    val id: Int?,
    val name: String?,
    val rec: Boolean?,
    val room: String?,
    val snapshot: String?,
)