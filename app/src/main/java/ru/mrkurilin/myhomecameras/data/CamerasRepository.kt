package ru.mrkurilin.myhomecameras.data

import kotlinx.coroutines.flow.Flow
import ru.mrkurilin.myhomecameras.data.local.LocalDataStorage
import ru.mrkurilin.myhomecameras.data.local.model.CameraEntity
import ru.mrkurilin.myhomecameras.data.remote.ApiService
import ru.mrkurilin.myhomecameras.data.remote.model.Camera

class CamerasRepository(
    private val localDataStorage: LocalDataStorage,
    private val apiService: ApiService,
) {

    suspend fun init(){
        if (localDataStorage.isCamerasEmpty()) {
            updateLocalData()
        }
    }

    suspend fun updateLocalData(): Boolean {
        val camerasResponseData = apiService.getCameras().data ?: throw UpdateLocalDataException()
        val remoteCameras = camerasResponseData.cameras ?: throw UpdateLocalDataException()

        saveRemoteCameras(remoteCameras)
        return true
    }

    fun getCamerasFlow(): Flow<List<CameraEntity>> = localDataStorage.getCamerasFlow()

    private fun saveRemoteCameras(remoteCameras: List<Camera>) {
        val cameraEntities = mutableListOf<CameraEntity>()
        for (remoteCameraModel in remoteCameras) {
            if (remoteCameraModel.id == null) {
                continue
            }

            cameraEntities.add(
                CameraEntity(
                    id = remoteCameraModel.id,
                    favorites = remoteCameraModel.favorites ?: false,
                    name = remoteCameraModel.name ?: "",
                    rec = remoteCameraModel.rec ?: false,
                    room = remoteCameraModel.room ?: "",
                    snapshot = remoteCameraModel.snapshot ?: "",
                )
            )
        }
        localDataStorage.setCameras(cameraEntities)
    }
}