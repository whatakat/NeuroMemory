package com.bankmtk.neuromemory.ui.splash

import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.errors.NoAuthException
import com.bankmtk.neuromemory.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class SplashViewModel(val repository: Repository):
BaseViewModel<Boolean?>(){
    @ExperimentalCoroutinesApi
    fun requestUser(){
        launch {
            repository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthException())
        }
    }
}