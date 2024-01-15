package ru.mrkurilin.myhomecameras.data

import kotlinx.coroutines.flow.Flow
import ru.mrkurilin.myhomecameras.data.local.LocalDataStorage
import ru.mrkurilin.myhomecameras.data.local.model.DoorEntity
import ru.mrkurilin.myhomecameras.data.remote.ApiService
import ru.mrkurilin.myhomecameras.data.remote.model.DoorsData

class DoorsRepository(
    private val localDataStorage: LocalDataStorage,
    private val apiService: ApiService,
) {

    suspend fun updateIfEmpty(){
        if (localDataStorage.isDoorsEmpty()) {
            updateLocalData()
        }
    }

    suspend fun updateLocalData() {
        val doorsResponseData  = apiService.getDoors().data ?: throw UpdateLocalDataException()
        saveRemoteDoors(doorsResponseData)
    }

    private fun saveRemoteDoors(remoteDoors: List<DoorsData>) {
        val doorEntities = mutableListOf<DoorEntity>()
        for (remoteDoor in remoteDoors) {
            if (remoteDoor.id == null) {
                continue
            }

            doorEntities.add(
                DoorEntity(
                    id = remoteDoor.id,
                    favorites = remoteDoor.favorites ?: false,
                    name = remoteDoor.name ?: "",
                    room = remoteDoor.room ?: "",
                    snapshot = remoteDoor.snapshot,
                )
            )
        }
        localDataStorage.setDoors(doorEntities)
    }

    fun getDoorsFlow(): Flow<List<DoorEntity>> = localDataStorage.getDoorsFlow()
}