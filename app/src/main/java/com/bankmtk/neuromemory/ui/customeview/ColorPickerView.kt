package com.bankmtk.neuromemory.ui.customeview

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

}