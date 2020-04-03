package com.bankmtk.neuromemory.ui.sticker

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.base.BaseViewState

class StickerViewState (sticker: Sticker? = null,
                        error: Throwable? = null): BaseViewState<Sticker?>(sticker,error)