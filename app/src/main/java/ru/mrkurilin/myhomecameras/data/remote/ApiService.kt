package ru.mrkurilin.myhomecameras.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import ru.mrkurilin.myhomecameras.data.remote.model.CamerasResponse
import ru.mrkurilin.myhomecameras.data.remote.model.DoorsResponse

private const val CAMERAS_ENDPOINT = "api/rubetek/cameras/"
private const val DOORS_ENDPOINT = "api/rubetek/doors/"

class ApiService(
    private val client: HttpClient,
) {

    suspend fun getCameras(): CamerasResponse {
        return client.get(CAMERAS_ENDPOINT).body()
    }

    suspend fun getDoors(): DoorsResponse {
        return client.get(DOORS_ENDPOINT).body()
    }
}