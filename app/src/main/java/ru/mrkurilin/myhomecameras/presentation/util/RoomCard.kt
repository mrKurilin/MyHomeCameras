package ru.mrkurilin.myhomecameras.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrkurilin.myhomecameras.presentation.camerasScreen.CameraCard
import ru.mrkurilin.myhomecameras.presentation.camerasScreen.model.CameraUiModel

@Composable
fun <T> RoomCard(
    roomName: String,
    contentItems: List<T>,
    roomContent: @Composable (T) -> Unit,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Комната: $roomName"
        )

        contentItems.forEach { item ->
            roomContent(
                item
            )
        }
    }
}

@Preview
@Composable
fun RoomCardPreview() {
    RoomCard(
        roomName = "Text",
        contentItems = listOf(
            CameraUiModel(
                0, true, "ads", false, "ads", "ads"
            )
        ),
        roomContent = { CameraCard(it) }
    )
}