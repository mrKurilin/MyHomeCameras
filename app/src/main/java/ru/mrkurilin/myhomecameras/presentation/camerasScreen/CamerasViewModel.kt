package ru.mrkurilin.myhomecameras.presentation.camerasScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.mrkurilin.myhomecameras.data.CamerasRepository
import ru.mrkurilin.myhomecameras.di.CamerasComponent
import ru.mrkurilin.myhomecameras.presentation.camerasScreen.model.CameraUiModel
import ru.mrkurilin.myhomecameras.presentation.util.model.RoomUiModel

class CamerasViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val camerasComponent = application as CamerasComponent
    private val camerasRepository: CamerasRepository = camerasComponent.provideCamerasRepository()

    private val camerasScreenStateMutableStateFlow = MutableStateFlow(CamerasScreenState())
    val camerasScreenStateStateFlow = camerasScreenStateMutableStateFlow.asStateFlow()

    private val ioDispatcher: CoroutineDispatcher = camerasComponent.provideIODispatcher()

    init {
        viewModelScope.launch(ioDispatcher) {
            camerasRepository.init()

            val cameraEntitiesFlow = camerasRepository.getCamerasFlow()

            val cameraUiModelsFlow = cameraEntitiesFlow.map { cameras ->
                val groupedCamerasByRooms = cameras.groupBy { it.room }

                val roomUiModelList = groupedCamerasByRooms.map GroupedCamerasByRoomsMap@{ entry ->
                    val cameraUiModels = entry.value.map { cameraEntity ->
                        CameraUiModel.fromEntity(cameraEntity)
                    }
                    return@GroupedCamerasByRoomsMap RoomUiModel(
                        name = entry.key,
                        roomItems = cameraUiModels
                    )
                }
                return@map roomUiModelList
            }

            cameraUiModelsFlow.collectLatest { roomUiModels ->
                camerasScreenStateMutableStateFlow.emit(
                    camerasScreenStateMutableStateFlow.value.copy(
                        rooms = roomUiModels,
                        updating = false
                    )
                )
            }
        }
    }

    fun updateCamerasRepository() {
        viewModelScope.launch(ioDispatcher) {
            camerasScreenStateMutableStateFlow.emit(
                camerasScreenStateMutableStateFlow.value.copy(
                    updating = true
                )
            )

            camerasRepository.updateLocalData()
        }
    }
}