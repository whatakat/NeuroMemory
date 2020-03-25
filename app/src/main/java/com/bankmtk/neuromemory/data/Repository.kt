package com.bankmtk.neuromemory.data

import com.bankmtk.neuromemory.data.model.Sticker

object Repository {
    private val stickers: List<Sticker>
    init {
        stickers = listOf(
            Sticker("Простите ребята, кто не успел - тот опоздал",
            "Sorry fellas waste not wan't not",0xfff06292.toInt())
        )
    }
    fun getStickers():List<Sticker>{
        return stickers
    }
}