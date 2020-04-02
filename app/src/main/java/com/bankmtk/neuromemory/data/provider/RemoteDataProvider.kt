package com.bankmtk.neuromemory.data.provider

import androidx.lifecycle.LiveData
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.StickerResult

interface RemoteDataProvider {
    fun subscribeToAllStickers(): LiveData<StickerResult>
    fun getStickerById(id: String): LiveData<StickerResult>
    fun saveSticker(sticker: Sticker): LiveData<StickerResult>
}