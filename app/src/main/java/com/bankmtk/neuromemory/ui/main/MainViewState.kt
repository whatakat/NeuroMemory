package com.bankmtk.neuromemory.ui.main

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.base.BaseViewState

class MainViewState (stickers: List<Sticker>? = null, error: Throwable? = null):
BaseViewState<List<Sticker>?>(stickers,error)
