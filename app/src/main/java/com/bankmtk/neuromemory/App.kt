package com.bankmtk.neuromemory

import androidx.multidex.MultiDexApplication
import com.bankmtk.neuromemory.di.appModule
import com.bankmtk.neuromemory.di.mainModule
import com.bankmtk.neuromemory.di.splashModule
import com.bankmtk.neuromemory.di.stickerModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.startKoin

class App: MultiDexApplication() {
    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, splashModule, mainModule, stickerModule))
    }
}