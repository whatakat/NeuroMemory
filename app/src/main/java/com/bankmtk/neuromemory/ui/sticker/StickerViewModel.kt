package com.bankmtk.neuromemory.ui.sticker

import androidx.lifecycle.Observer
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.sticker.StickerViewState.StickerData
import com.bankmtk.neuromemory.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class StickerViewModel(private val repository: Repository): BaseViewModel<StickerData>() {

    private val currentSticker: Sticker?
    get() = getViewState().poll()?.sticker

    fun saveChanges(sticker: Sticker){
        setData(StickerData(sticker = sticker))
    }

    fun loadSticker(stickerId: String){
       launch {
           try {
               repository.getStickerById(stickerId).let {
                   setData(StickerData(sticker = it))
               }
           }catch (e: Throwable){
               setError(e)
           }
       }
    }
    fun deleteSticker(){
        launch {
            try {
                currentSticker?.let {
                    repository.deleteSticker(it.id)}
                setData(StickerData(isDeleted = true))
            }catch (e: Throwable){
                setError(e)
            }
        }
    }
    override fun onCleared() {
        launch {
            currentSticker?.let { repository.saveSticker(it)}
            super.onCleared()
        }
    }
}