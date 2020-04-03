package com.bankmtk.neuromemory.data

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.data.provider.FireStoreProvider
import com.bankmtk.neuromemory.data.provider.RemoteDataProvider

object Repository {

    private val remoteProvider: RemoteDataProvider = FireStoreProvider()

    fun getStickers() = remoteProvider.subscribeToAllStickers()
    fun saveSticker(sticker: Sticker)= remoteProvider.saveSticker(sticker)
    fun getNoteById(id: String) =  remoteProvider.getStickerById(id)

}