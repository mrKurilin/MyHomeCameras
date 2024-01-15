package ru.mrkurilin.myhomecameras.presentation.doorsScreen

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
import ru.mrkurilin.myhomecameras.data.DoorsRepository
import ru.mrkurilin.myhomecameras.di.DoorsComponent
import ru.mrkurilin.myhomecameras.presentation.doorsScreen.DoorUiModel.Companion.toDoorUiModel
import ru.mrkurilin.myhomecameras.presentation.util.model.RoomUiModel

class DoorsViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val doorsComponent = application as DoorsComponent
    private val doorsRepository: DoorsRepository = doorsComponent.provideDoorsRepository()

    private val doorsStateMutableStateFlow = MutableStateFlow(DoorsUIState())
    val doorsUIStateStateFlow = doorsStateMutableStateFlow.asStateFlow()

    private val ioDispatcher: CoroutineDispatcher = doorsComponent.provideIODispatcher()

    init {
        viewModelScope.launch(ioDispatcher) {
            doorsRepository.updateIfEmpty()
            observeRepository()
        }
    }

    fun updateDoorsRepository() {
        viewModelScope.launch(ioDispatcher) {
            doorsStateMutableStateFlow.update { doorsUIState ->
                doorsUIState.copy(
                    updating = true,
                )
            }

            doorsRepository.updateLocalData()
        }
    }

    private suspend fun observeRepository() {
        val doorsEntitiesFlow = doorsRepository.getDoorsFlow()

        val doorUiModelsFlow = doorsEntitiesFlow.map { doors ->
            val groupedDoorsByRooms = doors.groupBy { it.room }

            val roomUiModelList = groupedDoorsByRooms.map GroupedDoorsByRoomsMap@{ entry ->
                val doorUiModels = entry.value.map { doorEntity ->
                    doorEntity.toDoorUiModel()
                }
                return@GroupedDoorsByRoomsMap RoomUiModel(
                    name = entry.key,
                    roomItems = doorUiModels
                )
            }
            return@map roomUiModelList
        }

        doorUiModelsFlow.collectLatest { roomUiModels ->
            doorsStateMutableStateFlow.update { doorsUIState ->
                doorsUIState.copy(
                    updating = false,
                    rooms = roomUiModels,
                )
            }
        }
    }
}