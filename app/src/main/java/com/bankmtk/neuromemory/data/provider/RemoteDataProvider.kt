package com.bankmtk.neuromemory.data.provider

import androidx.lifecycle.LiveData
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.data.model.User

interface RemoteDataProvider {
    fun subscribeToAllStickers(): LiveData<Result>
    fun getStickerById(id: String): LiveData<Result>
    fun saveSticker(sticker: Sticker): LiveData<Result>
    fun getCurrentUser() : LiveData<User?>
    fun deleteSticker(stickerId: String): LiveData<Result>
}