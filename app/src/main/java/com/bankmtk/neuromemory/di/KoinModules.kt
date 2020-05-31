package com.bankmtk.neuromemory.di

import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.provider.FireStoreProvider
import com.bankmtk.neuromemory.data.provider.RemoteDataProvider
import com.bankmtk.neuromemory.ui.main.MainViewModel
import com.bankmtk.neuromemory.ui.splash.SplashViewModel
import com.bankmtk.neuromemory.ui.sticker.StickerViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FireStoreProvider(get(),get()) } bind RemoteDataProvider::class
    single { Repository(get()) }
}
var splashModule = module{
    viewModel { SplashViewModel(get()) }
}
@ExperimentalCoroutinesApi
val mainModule = module {
    viewModel { MainViewModel(get()) }
}
val stickerModule = module {
    viewModel { StickerViewModel(get()) }
}
