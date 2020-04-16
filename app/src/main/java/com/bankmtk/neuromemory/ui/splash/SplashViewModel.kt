package com.bankmtk.neuromemory.ui.splash

import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.errors.NoAuthException
import com.bankmtk.neuromemory.ui.base.BaseViewModel

class SplashViewModel(private val repository: Repository):
BaseViewModel<Boolean?, SplashViewState>(){
    fun requestUser(){
        repository.getCurrentUser().observeForever{
            viewStateLiveData.value = if (it != null){
                SplashViewState(isAuth = true)
            }else{
                SplashViewState(error = NoAuthException())
            }
        }
    }
}