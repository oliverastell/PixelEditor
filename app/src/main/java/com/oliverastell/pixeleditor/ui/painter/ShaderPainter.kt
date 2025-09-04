package com.oliverastell.pixeleditor.ui.painter

import android.graphics.Shader
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.toIntSize
import androidx.core.graphics.createBitmap
import com.oliverastell.pixeleditor.common.BitmapEditor
import com.oliverastell.pixeleditor.common.paintShaderCrisp

class ShaderPainter(val shader: Shader, val renderSize: IntSize) : Painter() {
    override val intrinsicSize = Size.Unspecified

    override fun DrawScope.onDraw() {
        val bitmap = createBitmap(renderSize.width, renderSize.height)
        val editor = BitmapEditor(bitmap)

        editor.drawRect(0f, 0f, renderSize.width.toFloat(), renderSize.height.toFloat(),
            paintShaderCrisp(shader)
        )

        drawImage(
            bitmap.asImageBitmap(),
            dstSize = this@onDraw.size.toIntSize(),
            filterQuality = FilterQuality.None
        )
    }
}