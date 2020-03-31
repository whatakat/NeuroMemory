package com.bankmtk.neuromemory.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bankmtk.neuromemory.data.Repository

class MainViewModel : ViewModel() {

    private val viewStateLiveData: MutableLiveData<MainViewState> = MutableLiveData()
    init {
        Repository.getStickers().observeForever {
            viewStateLiveData.value = viewStateLiveData.value?.copy(stickers = it!!) ?:
                    MainViewState(it!!)
        }

    }
    fun viewState(): LiveData<MainViewState> = viewStateLiveData
}