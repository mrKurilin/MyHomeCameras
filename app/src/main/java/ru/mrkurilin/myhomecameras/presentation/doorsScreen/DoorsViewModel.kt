package ru.mrkurilin.myhomecameras.presentation.doorsScreen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.mrkurilin.myhomecameras.data.DoorsRepository
import ru.mrkurilin.myhomecameras.di.DoorsComponent
import ru.mrkurilin.myhomecameras.presentation.util.model.RoomUiModel

class DoorsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val doorsComponent = application as DoorsComponent
    private val doorsRepository: DoorsRepository = doorsComponent.provideDoorsRepository()

    private val doorsStateMutableStateFlow = MutableStateFlow(DoorsScreenState())
    val doorsStateStateFlow = doorsStateMutableStateFlow.asStateFlow()

    private val ioDispatcher: CoroutineDispatcher = doorsComponent.provideIODispatcher()

    init {
        viewModelScope.launch(ioDispatcher) {
            doorsRepository.init()
            val cameraEntitiesFlow = doorsRepository.getDoorsFlow()

            val cameraUiModelsFlow = cameraEntitiesFlow.map { cameras ->
                val groupedCamerasByRooms = cameras.groupBy { it.room }

                val roomUiModelList = groupedCamerasByRooms.map GroupedCamerasByRoomsMap@{ entry ->
                    val cameraUiModels = entry.value.map { doorEntity ->
                        DoorUiModel.fromDoorEntity(doorEntity)
                    }
                    return@GroupedCamerasByRoomsMap RoomUiModel(
                        name = entry.key,
                        roomItems = cameraUiModels
                    )
                }
                return@map roomUiModelList
            }

            cameraUiModelsFlow.collectLatest { roomUiModels ->
                doorsStateMutableStateFlow.emit(
                    doorsStateMutableStateFlow.value.copy(
                        updating = false,
                        rooms = roomUiModels,
                    )
                )
            }
        }
    }

    fun updateDoorsRepository() {
        viewModelScope.launch(ioDispatcher) {
            doorsStateMutableStateFlow.emit(
                doorsStateMutableStateFlow.value.copy(
                    updating = true,
                )
            )

            doorsRepository.updateLocalData()
        }
    }
}