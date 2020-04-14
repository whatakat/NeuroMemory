package com.bankmtk.neuromemory.ui.customeview

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP
import androidx.annotation.Dimension.PX
import androidx.core.content.ContextCompat
import com.bankmtk.neuromemory.R
import org.jetbrains.anko.dip

@Dimension(unit = DP) private const val defRadiusDp = 16
@Dimension(unit = DP) private const val defStrokeWidthDp = 1

class ColorCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
defStyleAttr: Int = 0): View(context, attrs, defStyleAttr){

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
    private var strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private var center: Pair<Float, Float> = 0f to 0f

    @Dimension(unit = PX) var radius: Float = dip(defRadiusDp).toFloat()

    @ColorRes var fillColorRes: Int = R.color.white
    set(value) {
        field = value
        fillPaint.color = ContextCompat.getColor(context,value)
    }
    @ColorRes var strokeColorRes: Int = R.color.black
        set(value) {
            field = value
            strokePaint.color = ContextCompat.getColor(context,value)
        }
}