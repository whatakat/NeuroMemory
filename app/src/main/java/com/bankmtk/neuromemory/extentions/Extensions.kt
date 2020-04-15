package com.bankmtk.neuromemory.extentions

import android.content.Context
import androidx.core.content.ContextCompat
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "dd.MMM.yy HH:mm"

fun Date.format(): String =
    SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault())
        .format(this)

fun Color.getColorInt(context: Context): Int =
    ContextCompat.getColor(context, getColorRes())

fun Color.getColorRes(): Int = when (this) {
    Color.WHITE -> R.color.white
    Color.GREEN -> R.color.green
    Color.YELLOW -> R.color.yellow
    Color.RED -> R.color.red
    Color.PINK -> R.color.pink
    Color.BLUE -> R.color.blue
    Color.VIOLET -> R.color.violet
}