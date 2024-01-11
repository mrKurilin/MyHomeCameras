package ru.mrkurilin.myhomecameras.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ru.mrkurilin.myhomecameras.data.CamerasRepository
import ru.mrkurilin.myhomecameras.data.DoorsRepository
import ru.mrkurilin.myhomecameras.data.local.LocalDataStorage
import ru.mrkurilin.myhomecameras.data.remote.ApiService

class AppModule {

    val iODispatcher: CoroutineDispatcher by lazy {
        Dispatchers.IO
    }

    private val apiService: ApiService by lazy {
        ApiService()
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