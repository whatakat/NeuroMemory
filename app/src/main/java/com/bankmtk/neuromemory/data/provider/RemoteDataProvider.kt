package com.bankmtk.neuromemory.data.provider

import androidx.lifecycle.LiveData
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.data.model.User

interface RemoteDataProvider {
    fun subscribeToAllStickers(): LiveData<Result>
    suspend fun getStickerById(id: String): Sticker
    suspend fun saveSticker(sticker: Sticker): Sticker
    suspend fun getCurrentUser() : User?
    suspend fun deleteSticker(stickerId: String)
}