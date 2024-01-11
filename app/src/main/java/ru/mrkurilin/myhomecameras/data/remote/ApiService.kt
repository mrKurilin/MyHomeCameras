package ru.mrkurilin.myhomecameras.data.remote

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import ru.mrkurilin.myhomecameras.data.remote.model.GetCamerasResponse
import ru.mrkurilin.myhomecameras.data.remote.model.GetDoorsResponse

class ApiService {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.i("HTTP Client", message, null)
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun getCameras(): GetCamerasResponse {
        return client.get(
            "http://cars.cprogroup.ru/api/rubetek/cameras/"
        ).body()
    }

    suspend fun getDoors(): GetDoorsResponse {
        return client.get(
            "http://cars.cprogroup.ru/api/rubetek/doors/"
        ).body()
    }
}