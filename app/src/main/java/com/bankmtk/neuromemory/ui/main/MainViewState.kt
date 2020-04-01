package com.bankmtk.neuromemory.ui.main

import com.bankmtk.neuromemory.data.model.Sticker

class MainViewState (val stickers: List<Sticker>) {
    fun copy(stickers: List<Sticker>): MainViewState? {
        return null
    }

}