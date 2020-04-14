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
    @Dimension(unit = PX) var strokeWidth: Float =
        dip(defStrokeWidthDp).toFloat()
    set(value){
        field = value
        strokePaint.strokeWidth = value
    }
    init {
        val a = context.obtainStyledAttributes(attrs,
        R.styleable.ColorCircleView)

        val defRadiusPx = dip(defRadiusDp).toFloat()
        radius = a.getDimension(R.styleable.ColorCircleView_circleRadius,
        defRadiusPx)
        fillColorRes = a.getResourceId(
            R.styleable.ColorCircleView_fillColor, R.color.white)
        val defStrokeWidthPx = dip(defStrokeWidthDp).toFloat()
        strokeWidth = a.getDimension(
            R.styleable.ColorCircleView_strokeWith, defStrokeWidthPx)
        strokeColorRes = a.getResourceId(
            R.styleable.ColorCircleView_strokeColor,
            R.color.color_text_secondary)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = (radius*2 + paddingTop + paddingBottom).toInt()
        val width = (radius*2 + paddingStart + paddingEnd).toInt()
        setMeasuredDimension(width,height)
    }
}