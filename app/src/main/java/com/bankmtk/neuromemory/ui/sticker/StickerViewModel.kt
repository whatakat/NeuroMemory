package com.bankmtk.neuromemory.ui.sticker

import androidx.lifecycle.Observer
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.sticker.StickerViewState.StickerData
import com.bankmtk.neuromemory.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class StickerViewModel(private val repository: Repository): BaseViewModel<StickerData>() {

    @ExperimentalCoroutinesApi
    private val currentSticker: Sticker?
    get() = getViewState().poll()?.sticker

    @ExperimentalCoroutinesApi
    fun saveChanges(sticker: Sticker){
        setData(StickerData(sticker = sticker))
    }

    @ExperimentalCoroutinesApi
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
    @ExperimentalCoroutinesApi
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
   @ExperimentalCoroutinesApi
   public override fun onCleared() {
        launch {
            currentSticker?.let { repository.saveSticker(it)}
            super.onCleared()
        }
    }
}