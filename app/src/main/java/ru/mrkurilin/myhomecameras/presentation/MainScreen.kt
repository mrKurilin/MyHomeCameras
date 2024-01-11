package ru.mrkurilin.myhomecameras.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mrkurilin.myhomecameras.presentation.camerasScreen.CamerasScreen
import ru.mrkurilin.myhomecameras.presentation.doorsScreen.DoorsScreen

@Composable
fun MainScreen() {

    var tabIndex by remember { mutableIntStateOf(0) }

    val tabs = listOf("Камеры", "Двери")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val style =
            MaterialTheme.typography.titleSmall
                .copy(textAlign = TextAlign.Center)
        ProvideTextStyle(style){
            Text(
                modifier = Modifier
                    .height(125.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background
                    )
                    .wrapContentHeight(),
                text = "Мой дом",
                textAlign = TextAlign.Center,
            )
        }

        TabRow(
            selectedTabIndex = tabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> CamerasScreen()
            1 -> DoorsScreen()
        }
    }
}

@Preview(
    apiLevel = 33
)
@Composable
fun MainScreenPreview() {
    MainScreen()
}