package ru.mrkurilin.myhomecameras.data.local

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import ru.mrkurilin.myhomecameras.data.local.model.CameraEntity
import ru.mrkurilin.myhomecameras.data.local.model.DoorEntity
import java.util.concurrent.Executors

class LocalDataStorage {

    private val realmDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val realm: Realm by lazy {
        runBlocking(realmDispatcher) {
            val config =
                RealmConfiguration.create(schema = setOf(CameraEntity::class, DoorEntity::class))
            Realm.open(config)
        }
    }

    fun setCameras(cameras: List<CameraEntity>) {
        realm.writeBlocking {
            val camerasLeftInTheRealm = query<CameraEntity>().find()
            delete(camerasLeftInTheRealm)

            cameras.forEach { cameraEntity ->
                copyToRealm(cameraEntity)
            }
        }
    }

    fun setDoors(doors: List<DoorEntity>) {
        realm.writeBlocking {
            val doorsLeftInTheRealm = query<DoorEntity>().find()
            delete(doorsLeftInTheRealm)

            doors.forEach { cameraEntity ->
                copyToRealm(cameraEntity)
            }
        }
    }

    fun getCamerasFlow(): Flow<List<CameraEntity>> {
        return realm.query<CameraEntity>().asFlow().map {
            it.list.toList()
        }
    }

    fun getDoorsFlow(): Flow<List<DoorEntity>> {
        return realm.query<DoorEntity>().asFlow().map {
            it.list.toList()
        }
    }

    fun isDoorsEmpty(): Boolean = realm.query<DoorEntity>().find().isEmpty()

    fun isCamerasEmpty(): Boolean = realm.query<CameraEntity>().find().isEmpty()
}