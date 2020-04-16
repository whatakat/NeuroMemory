package com.bankmtk.neuromemory

import androidx.multidex.MultiDexApplication
import com.bankmtk.neuromemory.di.appModule
import com.bankmtk.neuromemory.di.mainModule
import com.bankmtk.neuromemory.di.splashModule
import com.bankmtk.neuromemory.di.stickerModule
import org.koin.android.ext.android.startKoin

class App: MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule, splashModule, mainModule, stickerModule))
    }
}