package com.bankmtk.neuromemory.ui.sticker

import androidx.lifecycle.Observer
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.ui.base.BaseViewModel

class StickerViewModel(val repository: Repository = Repository): BaseViewModel<StickerViewState.Data, StickerViewState>() {
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
        repository.getStickerById(stickerId).observeForever{ t ->
            t?.let {
                viewStateLiveData.value = when (t) {
                    is Result.Success<*> -> StickerViewState(StickerViewState.Data(sticker = t.data as? Sticker))
                    is Result.Error-> StickerViewState(error = t.error)
                }
            }
        }
    }
    private val currentSticker: Sticker?
        get() = viewStateLiveData.value?.data?.sticker

    fun deleteSticker(){
        currentSticker?.let{
            repository.deleteSticker(it.id).observeForever{ t ->
                t?.let {
                    viewStateLiveData.value = when(it){
                        is Result.Success<*> -> StickerViewState(StickerViewState.Data(isDeleted = true))
                        is Result.Error -> StickerViewState(error = it.error)
                    }
                }
            }
        }
    }

}