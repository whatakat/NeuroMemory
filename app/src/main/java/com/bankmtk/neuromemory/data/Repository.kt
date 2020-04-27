package com.bankmtk.neuromemory.data

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.provider.FireStoreProvider
import com.bankmtk.neuromemory.data.provider.RemoteDataProvider

class Repository(private val remoteProvider: RemoteDataProvider) {

    fun getStickers() = remoteProvider.subscribeToAllStickers()
    suspend fun saveSticker(sticker: Sticker)= remoteProvider.saveSticker(sticker)
    suspend fun getStickerById(id: String) =  remoteProvider.getStickerById(id)
    suspend fun getCurrentUser() = remoteProvider.getCurrentUser()
    suspend fun deleteSticker(stickerId: String) = remoteProvider.deleteSticker(stickerId)

}