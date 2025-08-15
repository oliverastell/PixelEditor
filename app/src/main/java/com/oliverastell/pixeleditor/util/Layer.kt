package com.oliverastell.pixeleditor.util

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.createBitmap
import com.oliverastell.pixeleditor.util.collections.ColorArray
import com.oliverastell.pixeleditor.util.collections.toInt
import java.nio.IntBuffer

// borrowed from https://stackoverflow.com/a/70454857
fun androidBitmapFormatRGBA8888(color: Int): Int {
    val a = (color shr 24) and 255
    val r = (color shr 16) and 255
    val g = (color shr 8) and 255
    val b = color and 255

    return (a shl 24) or (b shl 16) or (g shl 8) or r
}

@Stable
data class Layer(
    val width: Int,
    val height: Int,
    val opacity: Float = 1F,
    val blendMode: BlendMode = BlendMode.SrcOver,
    private val version: Int = 0, // this is just a hacky way to make compose see this as a different item
    private val colors: ColorArray
) {
    private val buffer = IntBuffer.allocate(colors.size)

    constructor(
        width: Int,
        height: Int,
        opacity: Float = 1F,
        blendMode: BlendMode = BlendMode.SrcOver,
        version: Int = 0,
    ): this(width, height, opacity, blendMode, version, ColorArray(width*height))

    operator fun get(
        x: Int,
        y: Int
    ): Color = colors[x+y*width]

    /**
     * This will not trigger a recomposition.
     */
    fun setColorAt(x: Int, y: Int, color: Color) {
        colors[x+y*width] = color
    }

    /**
     * Please note this does mutate the underlying list for performance reasons.
     * It's not ideal, but we're working with what Jetpack Compose will let us.
     */
    fun withColorAt(
        x: Int,
        y: Int,
        color: Color
    ): Layer {
        if (x < 0 || x >= width || y < 0 || y >= height)
            return this
        setColorAt(x, y, color)
        return Layer(width, height, opacity, blendMode, version+1, colors)
    }

    fun withOpacity(opacity: Float) =
        Layer(width, height, opacity, blendMode, version+1)

    fun withBlendMode(blendMode: BlendMode) =
        Layer(width, height, opacity, blendMode, version+1)

    fun toImageBitmap(): ImageBitmap {
        val bitmap = createBitmap(width, height, Bitmap.Config.ARGB_8888)

        buffer.rewind()
        for (color in colors) {
            // For some reason bitmaps store their colors as ABGR instead of ARGB for some odd reason
            //   here's the fix
            buffer.put(androidBitmapFormatRGBA8888(color.toInt()))
        }
        buffer.rewind()

        bitmap.copyPixelsFromBuffer(buffer)

        return bitmap.asImageBitmap()
    }
}