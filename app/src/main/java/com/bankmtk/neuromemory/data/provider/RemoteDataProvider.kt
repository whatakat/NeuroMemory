package com.bankmtk.neuromemory.data.provider

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.model.Result
import com.bankmtk.neuromemory.data.model.User
import kotlinx.coroutines.channels.ReceiveChannel

interface RemoteDataProvider {
    fun subscribeToAllStickers(): ReceiveChannel<Result>
    suspend fun getStickerById(id: String): Sticker
    suspend fun saveSticker(sticker: Sticker): Sticker
    suspend fun getCurrentUser() : User?
    suspend fun deleteSticker(stickerId: String)
}