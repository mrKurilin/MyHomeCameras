package ru.mrkurilin.myhomecameras.presentation.camerasScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.mrkurilin.myhomecameras.data.CamerasRepository
import ru.mrkurilin.myhomecameras.di.CamerasComponent
import ru.mrkurilin.myhomecameras.presentation.camerasScreen.model.CameraUiModel.Companion.toCameraUiModel
import ru.mrkurilin.myhomecameras.presentation.util.model.RoomUiModel

class CamerasViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val camerasComponent = application as CamerasComponent
    private val camerasRepository: CamerasRepository = camerasComponent.provideCamerasRepository()

    private val camerasUIStateMutableStateFlow = MutableStateFlow(CamerasUIState())
    val camerasUIStateStateFlow = camerasUIStateMutableStateFlow.asStateFlow()

    private val ioDispatcher: CoroutineDispatcher = camerasComponent.provideIODispatcher()

    init {
        viewModelScope.launch(ioDispatcher) {
            camerasRepository.updateIfEmpty()
            observeRepository()
        }
    }

    fun updateCamerasRepository() {
        viewModelScope.launch(ioDispatcher) {
            camerasUIStateMutableStateFlow.update { camerasUIState ->
                camerasUIState.copy(
                    updating = true
                )
            }

            camerasRepository.updateLocalData()
        }
    }

    private suspend fun observeRepository() {
        val cameraEntitiesFlow = camerasRepository.getCamerasFlow()

        val cameraUiModelsFlow = cameraEntitiesFlow.map { cameras ->
            val groupedCamerasByRooms = cameras.groupBy { it.room }

            val roomUiModelList = groupedCamerasByRooms.map GroupedCamerasByRoomsMap@{ entry ->
                val cameraUiModels = entry.value.map { cameraEntity ->
                    cameraEntity.toCameraUiModel()
                }
                return@GroupedCamerasByRoomsMap RoomUiModel(
                    name = entry.key,
                    roomItems = cameraUiModels
                )
            }
            return@map roomUiModelList
        }

        cameraUiModelsFlow.collectLatest { roomUiModels ->
            camerasUIStateMutableStateFlow.update { camerasUIState ->
                camerasUIState.copy(
                    rooms = roomUiModels,
                    updating = false
                )
            }
        }
    }
}