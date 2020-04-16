package com.bankmtk.neuromemory.di

import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.provider.FireStoreProvider
import com.bankmtk.neuromemory.data.provider.RemoteDataProvider
import com.bankmtk.neuromemory.ui.main.MainViewModel
import com.bankmtk.neuromemory.ui.splash.SplashViewModel
import com.bankmtk.neuromemory.ui.sticker.StickerViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(),get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}
var splashModule = module{
    factory { SplashViewModel(get()) }
}
val mainModule = module {
    factory { MainViewModel(get()) }
}
val noteModule = module {
    factory { StickerViewModel(get()) }
}