package ru.mrkurilin.myhomecameras.di

import kotlinx.coroutines.CoroutineDispatcher
import ru.mrkurilin.myhomecameras.data.CamerasRepository

interface CamerasComponent {

    fun provideCamerasRepository(): CamerasRepository

    fun provideIODispatcher(): CoroutineDispatcher
}