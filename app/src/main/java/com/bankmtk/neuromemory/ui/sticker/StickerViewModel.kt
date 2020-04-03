package com.bankmtk.neuromemory.ui.sticker

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.StickerResult
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
        repository.getStickerById(stickerId).observeForever(object : Observer<StickerResult>{
            override fun onChanged(t: StickerResult?) {
                if (t == null) return
                when(t){
                    is StickerResult.Success<*>->
                        viewStateLiveData.value = StickerViewState(sticker = t.data as? Sticker)
                    is StickerResult.Error ->
                        viewStateLiveData.value = StickerViewState(error = t.error)
                }
            }
        })
    }

}