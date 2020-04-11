package com.bankmtk.neuromemory.ui.main

import androidx.lifecycle.Observer
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.ui.base.BaseViewModel

class MainViewModel(val repository: Repository = Repository) : BaseViewModel<List<Sticker>?,MainViewState>() {
    private val stickersObserver = object : Observer<Result>{
        override fun onChanged(t: Result?) {
            if (t==null) return

            when(t){
                is com.bankmtk.neuromemory.data.model.StickerResult.Result.Success<*> ->{
                    viewStateLiveData.value = MainViewState(stickers = t.data as? List<Sticker>)
                }
                is com.bankmtk.neuromemory.data.model.StickerResult.Result.Error ->{
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }
    private val repositoryStickers = repository.getStickers()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryStickers.observeForever(stickersObserver)
    }

    override fun onCleared() {
        repositoryStickers.removeObserver(stickersObserver)
    }
}