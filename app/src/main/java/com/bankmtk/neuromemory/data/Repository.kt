package com.bankmtk.neuromemory.data

import com.bankmtk.neuromemory.data.model.Stick

object Repository {
    private val stickers: List<Stick>
    init {
        stickers = listOf(
            Stick("Простите ребята, кто не успел - тот опоздал",
            "Sorry fellas waste not wan't not",0xfff06292.toInt())
        )
    }
    fun getStickers():List<Stick>{
        return stickers
    }
}