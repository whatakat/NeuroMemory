package com.bankmtk.neuromemory.ui.main

import androidx.annotation.VisibleForTesting
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainViewModel(val repository: Repository) : BaseViewModel<List<Sticker>?>() {
    private val stickerChannel = repository.getStickers()

    init {
        launch {
            stickerChannel.consumeEach {
                when(it){
                    is Result.Success<*> -> setData(it.data as? List<Sticker>)
                    is Result.Error -> setError(it.error)
                }
            }
        }
    }

    @VisibleForTesting
    public override fun onCleared(){
        stickerChannel.cancel()
        super.onCleared()
    }
}