package com.bankmtk.neuromemory.ui.sticker

import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.ui.base.BaseViewState

class StickerViewState (data: Data = Data(),
                        error: Throwable? = null): BaseViewState<StickerViewState.Data>(data,error){
    data class Data(val isDeleted: Boolean = false, val sticker: Sticker? = null)
}