package ru.mrkurilin.myhomecameras.presentation.camerasScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.mrkurilin.myhomecameras.presentation.util.RoomCard

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CamerasScreen(
    camerasViewModel: CamerasViewModel = viewModel()
) {
    val camerasUIState by camerasViewModel.camerasUIStateStateFlow.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = camerasUIState.updating,
        onRefresh = camerasViewModel::updateCamerasRepository
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp),
        ) {
            items(camerasUIState.rooms.size) { index ->
                val roomUiModel = camerasUIState.rooms[index]

                RoomCard(
                    roomName = roomUiModel.name,
                    contentItems = roomUiModel.roomItems,
                    roomContent = { cameraUiModel -> CameraCard(cameraUiModel) }
                )
            }
        }

        PullRefreshIndicator(
            refreshing = camerasUIState.updating,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = if (camerasUIState.updating) Color.Red else Color.Green,
        )
    }
}