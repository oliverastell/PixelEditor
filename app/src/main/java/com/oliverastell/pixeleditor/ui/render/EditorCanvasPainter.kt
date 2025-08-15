package com.oliverastell.pixeleditor.ui.render

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.toIntSize
import com.oliverastell.pixeleditor.util.Editor

class EditorCanvasPainter(val editor: Editor) : Painter() {

    override val intrinsicSize: Size = Size(editor.width.toFloat(), editor.height.toFloat())

    override fun DrawScope.onDraw() {
        drawRect(
            Color.White,
            size = this@onDraw.size,
            topLeft = Offset.Zero
        )

        drawImage(
            editor.backingImage,
            dstSize = this@onDraw.size.toIntSize(),
            filterQuality = FilterQuality.Companion.None
        )

        for (layerIdx in 0..<editor.layerCount) {
            drawImage(
                editor[layerIdx].toImageBitmap(),
                alpha = editor[layerIdx].opacity,
                blendMode = editor[layerIdx].blendMode,
                dstSize = this@onDraw.size.toIntSize(),
                filterQuality = FilterQuality.Companion.None
            )
        }
    }

}