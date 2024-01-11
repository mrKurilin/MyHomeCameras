package ru.mrkurilin.myhomecameras

import android.app.Application
import kotlinx.coroutines.CoroutineDispatcher
import ru.mrkurilin.myhomecameras.data.CamerasRepository
import ru.mrkurilin.myhomecameras.data.DoorsRepository
import ru.mrkurilin.myhomecameras.di.AppModule
import ru.mrkurilin.myhomecameras.di.CamerasComponent
import ru.mrkurilin.myhomecameras.di.DoorsComponent

class MyHomeApplication : Application(), CamerasComponent, DoorsComponent {

    private val appModule: AppModule by lazy {
        AppModule()
    }

    override fun provideCamerasRepository(): CamerasRepository = appModule.camerasRepository

    override fun provideDoorsRepository(): DoorsRepository = appModule.doorsRepository

    override fun provideIODispatcher(): CoroutineDispatcher = appModule.iODispatcher
}