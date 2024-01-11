package ru.mrkurilin.myhomecameras.di

import kotlinx.coroutines.CoroutineDispatcher
import ru.mrkurilin.myhomecameras.data.DoorsRepository

interface DoorsComponent {

    fun provideDoorsRepository(): DoorsRepository

    fun provideIODispatcher(): CoroutineDispatcher
}