package com.bankmtk.neuromemory.ui.customeview

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.Dimension
import androidx.annotation.Dimension.DP

@Dimension(unit = DP) private const val defRadiusDp = 16
@Dimension(unit = DP) private const val defStrokeWidthDp = 1

class ColorCircleView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
defStyleAttr: Int = 0): View(context, attrs, defStyleAttr){

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }
}