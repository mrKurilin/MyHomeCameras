package ru.mrkurilin.myhomecameras.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.mrkurilin.myhomecameras.presentation.theme.MyHomeCamerasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            MyHomeCamerasTheme {
                MainScreen()
            }
        }
    }
}