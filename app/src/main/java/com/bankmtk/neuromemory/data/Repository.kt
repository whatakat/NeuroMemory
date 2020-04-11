package com.bankmtk.neuromemory.data

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.provider.FireStoreProvider
import com.bankmtk.neuromemory.data.provider.RemoteDataProvider

object Repository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getStickers() = remoteProvider.subscribeToAllStickers()
    fun saveSticker(sticker: Sticker)= remoteProvider.saveSticker(sticker)
    fun getStickerById(id: String) =  remoteProvider.getStickerById(id)
    fun getCurrentUser() = remoteProvider.getCurrentUser()
    fun deleteSticker(stickerId: String) = remoteProvider.deleteSticker(stickerId)

}