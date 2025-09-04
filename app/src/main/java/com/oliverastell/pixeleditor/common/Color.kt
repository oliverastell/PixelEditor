package com.oliverastell.pixeleditor.common

import android.graphics.BlendMode
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Shader
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.colorspace.ColorSpaces

fun Color.toULong() = this.value

/**
 * Will return a color in the Srgb format: {alpha: 8bit} {r: 8bit} {g: 8bit} {b: 8bit}
 */
fun Color.toInt() = (this.convert(ColorSpaces.Srgb).value shr 32).toInt()
fun Color.toUInt() = (this.convert(ColorSpaces.Srgb).value shr 32).toUInt()

fun paintShader(shader: Shader): Paint {
    val paint = Paint()
    paint.shader = shader
    paint.blendMode = BlendMode.SRC
    return paint
}

fun paintShaderCrisp(shader: Shader): Paint {
    val paint = paintShader(shader)
    paint.isAntiAlias = false
    return paint
}
