package com.bankmtk.neuromemory.extentions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.Menu
import android.view.View
import androidx.core.content.ContextCompat
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_FORMAT = "dd.MM.yy HH:mm"

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
fun rotateFab(v: View, rotate: Boolean): Boolean {
    v.animate().setDuration(350)
        .setListener(object : AnimatorListenerAdapter() {
        })
        .rotation(if (rotate) -100f else 0f)
    return rotate
}
fun showIn(v: View) {
    v.visibility = View.VISIBLE
    v.alpha = 0f
    v.translationX = v.height.toFloat()
    v.animate()
        .setDuration(360)
        .translationX(0f)
        .setListener(object : AnimatorListenerAdapter() {
        })
        .alpha(0.5f)
        .start()
}
fun showOut(v: View) {
    v.visibility = View.VISIBLE
    v.alpha = 0.5f
    v.translationX = 0f
    v.animate()
        .setDuration(360)
        .translationX(v.height.toFloat())
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                v.visibility = View.GONE
                super.onAnimationEnd(animation)
            }
        }).alpha(0f)
        .start()
}
fun init(v: View) {
    v.visibility = View.GONE
    v.translationX = v.height.toFloat()
    v.alpha = 0f
}
fun Menu.isContains(str: String):Boolean{
    (0 until this.size()).map {
        if (this.getItem(it).title==str){
                return true
            }}
    return false
    }
