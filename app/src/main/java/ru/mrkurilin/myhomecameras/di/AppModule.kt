package ru.mrkurilin.myhomecameras.di

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.mrkurilin.myhomecameras.BuildConfig
import ru.mrkurilin.myhomecameras.data.CamerasRepository
import ru.mrkurilin.myhomecameras.data.DoorsRepository
import ru.mrkurilin.myhomecameras.data.local.LocalDataStorage
import ru.mrkurilin.myhomecameras.data.remote.ApiService

class AppModule {

    val iODispatcher: CoroutineDispatcher = Dispatchers.IO

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.i("HTTP Client", message, null)
                }
            }
            level = LogLevel.ALL
        }
        defaultRequest {
            url(BuildConfig.BASE_URL)
        }
    }

    private val apiService: ApiService by lazy {
        ApiService(
            httpClient,
        )
    }

    private val localDataStorage: LocalDataStorage by lazy {
        LocalDataStorage()
    }

    val camerasRepository: CamerasRepository by lazy {
        CamerasRepository(
            localDataStorage = localDataStorage,
            apiService = apiService,
        )
    }

    val doorsRepository: DoorsRepository by lazy {
        DoorsRepository(
            localDataStorage = localDataStorage,
            apiService = apiService,
        )
    }
}