package com.bankmtk.neuromemory.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Sticker (val id: String="",
                    val title: String="",
                    val langOne: String="",
                    val langTwo: String="",
                    val color: Color = Color.WHITE,
                    var lastChanged: Date = Date()):Parcelable{
    override fun equals(other: Any?): Boolean {
       if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sticker
        if (id != other.id) return  false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

enum class Color{
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}