package com.bankmtk.neuromemory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker
import java.util.*

object Repository {
    private val stickersLiveData = MutableLiveData<List<Sticker>>()
    private val stickers: MutableList<Sticker> = mutableListOf(
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Простите ребята, кто не успел - тот опоздал",
            langTwo = "Sorry fellas waste not wan't not",
            color = Color.WHITE,
        title = ""),
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Что ты думаешь на счет этого",
            langTwo = "What do you mind about this",
            color = Color.GREEN,
        title = ""),
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Ты шутишь?",
            langTwo = "Are you kidding?",
            color = Color.WHITE,
        title = ""),
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Можешь дать мне подсказку",
            langTwo = "can you give me a clue?",
            color = Color.WHITE,
        title = ""))
    init {
        stickersLiveData.value = stickers
    }

    fun getStickers():LiveData<List<Sticker>>{
        return stickersLiveData
    }
    fun saveSticker(sticker: Sticker){
        addOrReplace(sticker)
        stickersLiveData.value = stickers
    }
    private fun addOrReplace(sticker: Sticker){
        for (i in 0 until stickers.size){
            if (stickers[i]==sticker){
                stickers[i] = sticker
                return
            }   
        }
        stickers.add(sticker)
    }
}