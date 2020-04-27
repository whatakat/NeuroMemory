package com.bankmtk.neuromemory.ui.sticker

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.base.BaseViewState

class StickerViewState (data: StickerData = StickerData(),
                        error: Throwable? = null): BaseViewState<StickerViewState.StickerData>(data,error){
    data class StickerData(val isDeleted: Boolean = false, val sticker: Sticker? = null)
}