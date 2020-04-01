package com.bankmtk.neuromemory.data.model

sealed class StickerResult {
    data class Success<out T>(val data: T): StickerResult()
    data class Error(val error: Throwable): StickerResult()
}