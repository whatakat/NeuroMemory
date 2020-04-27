package com.bankmtk.neuromemory.ui.splash

import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.errors.NoAuthException
import com.bankmtk.neuromemory.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(private val repository: Repository):
BaseViewModel<Boolean>(){
    fun requestUser(){
        launch {
            repository.getCurrentUser()?.let {
                setData(true)
            } ?: setError(NoAuthException())
        }
    }
}