package ru.mrkurilin.myhomecameras.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DoorsData(
    val id: Int?,
    val favorites: Boolean?,
    val name: String?,
    val room: String?,
    val snapshot: String = "",
)