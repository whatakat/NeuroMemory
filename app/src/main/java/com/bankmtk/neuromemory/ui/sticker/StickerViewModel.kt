package com.bankmtk.neuromemory.ui.sticker

import androidx.lifecycle.Observer
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.ui.base.BaseViewModel

class StickerViewModel(val repository: Repository = Repository): BaseViewModel<Sticker?, StickerViewState>() {
    private var pendingSticker: Sticker? = null
    fun saveChanges(sticker: Sticker){
        pendingSticker = sticker
    }

    override fun onCleared() {
        if (pendingSticker != null){
            repository.saveSticker(pendingSticker!!)
        }
    }
    fun loadSticker(stickerId: String){
        repository.getStickerById(stickerId).observeForever(object : Observer<Result>{
            override fun onChanged(t: Result?) {
                if (t == null) return
                when(t){
                    is com.bankmtk.neuromemory.data.model.StickerResult.Result.Success<*>->
                        viewStateLiveData.value = StickerViewState(sticker = t.data as? Sticker)
                    is com.bankmtk.neuromemory.data.model.StickerResult.Result.Error ->
                        viewStateLiveData.value = StickerViewState(error = t.error)
                }
            }
        })
    }

}