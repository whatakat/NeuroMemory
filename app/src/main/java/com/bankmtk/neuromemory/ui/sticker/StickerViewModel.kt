package com.bankmtk.neuromemory.ui.sticker

import androidx.lifecycle.ViewModel
import com.bankmtk.neuromemory.data.Repository
import com.bankmtk.neuromemory.data.model.Sticker

class StickerViewModel(private val repository: Repository = Repository): ViewModel() {
    private var pendingSticker: Sticker? = null
    fun saveChanges(sticker: Sticker){
        pendingSticker = sticker
    }

    override fun onCleared() {
        if (pendingSticker != null){
            repository.saveSticker(pendingSticker!!)
        }
    }
}