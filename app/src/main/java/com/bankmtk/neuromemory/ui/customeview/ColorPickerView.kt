package com.bankmtk.neuromemory.ui.customeview

import android.animation.ValueAnimator
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import com.bankmtk.neuromemory.data.model.Color

private const val PALETTE_ANIMATION_DURATION = 150L
private const val HEIGHT = "height"
private const val SCALE = "scale"
@Dimension(unit = DP) private const val COLOR_VIEW_PADDING = 8
class ColorPickerView: LinearLayout {
    var onColorClickListener: (color:Color) ->Unit = {}

    val isOpen: Boolean
    get() = measuredHeight>0

    private var desiredHeight = 0

    private val animator by lazy{
        ValueAnimator().apply {
            duration = PALETTE_ANIMATION_DURATION
            addUpdateListener(updateListener)
        }
    }
    private val updateListener by lazy {
        ValueAnimator.AnimatorUpdateListener { animator ->
            layoutParams.apply {
                height = animator.getAnimatedValue(HEIGHT) as Int
            }.let {
                layoutParams = it
            }
            val scaleFactor = animator.getAnimatedValue(SCALE) as Float
            for (i in 0 until childCount){
                getChildAt(i).apply {
                    scaleX = scaleFactor
                    scaleY = scaleFactor
                    alpha = scaleFactor
                }
            }
        }
    }


}