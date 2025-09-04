package com.oliverastell.pixeleditor.util

import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import com.oliverastell.pixeleditor.common.BitmapEditor
import com.oliverastell.pixeleditor.common.toInt

class Layer(val editor: Editor, defaultOpacity: Float = 1f, defaultBlend: BlendMode = BlendMode.SrcOver) {
    private val bitmap = createBitmap(editor.width, editor.height)
    private val canvas = BitmapEditor(bitmap)

    var opacity by mutableFloatStateOf(defaultOpacity)
    var blendMode by mutableStateOf(defaultBlend)
    var version by mutableIntStateOf(0)

    fun asBitmap() = bitmap
    fun asImageBitmap() = bitmap.asImageBitmap()

    operator fun get(x: Int, y: Int) = Color(bitmap.getPixel(x, y))
    operator fun set(x: Int, y: Int, color: Color) = bitmap.setPixel(x, y, color.toInt())

    fun drawCircle(x: Float, y: Float, radius: Float, paint: Paint) {
        canvas.drawCircle(
            x,
            y,
            radius,
            paint
        )
        editor.notifyCanvasChange()
    }

    fun drawSquare(x: Float, y: Float, radius: Float, paint: Paint) {
        canvas.drawRect(
            x-radius/2,
            y-radius/2,
            x+radius/2,
            y+radius/2,
            paint
        )
        editor.notifyCanvasChange()
    }

    fun drawLine(ax: Float, ay: Float, bx: Float, by: Float, radius: Float = 1f, paint: Paint) {
        canvas.drawLine(
            ax,
            ay,
            bx,
            by,
            paint.apply {
                strokeWidth = radius * 2
            }
        )
        editor.notifyCanvasChange()
    }
}