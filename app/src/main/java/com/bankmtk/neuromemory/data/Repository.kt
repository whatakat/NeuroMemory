package com.bankmtk.neuromemory.data

import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker
import java.util.*

object Repository {
    private val stickers: MutableList<Sticker> = mutableListOf(
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Простите ребята, кто не успел - тот опоздал",
            langTwo = "Sorry fellas waste not wan't not",
            color = Color.WHITE),
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Что ты думаешь на счет этого",
            langTwo = "What do you mind about this",
            color = Color.WHITE),
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Ты шутишь?",
            langTwo = "Are you kidding?",
            color = Color.WHITE),
        Sticker(id = UUID.randomUUID().toString(),
            langOne = "Можешь дать мне подсказку",
            langTwo = "can you give me a clue?",
            color = Color.WHITE))

    fun getStickers():List<Sticker>{
        return stickers
    }
}