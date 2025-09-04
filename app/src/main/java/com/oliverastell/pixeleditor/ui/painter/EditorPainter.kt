package com.oliverastell.pixeleditor.ui.painter

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.toIntSize
import com.oliverastell.pixeleditor.util.Editor

class EditorPainter(val editor: Editor) : Painter() {

    override val intrinsicSize: Size = Size(editor.width.toFloat(), editor.height.toFloat())

    override fun DrawScope.onDraw() {
        drawImage(
            editor.backingImage,
            dstSize = this@onDraw.size.toIntSize(),
            filterQuality = FilterQuality.Companion.None
        )

        for (layerIdx in 0..<editor.layerCount) {
            drawImage(
                editor.getLayer(layerIdx).asImageBitmap(),
                alpha = editor.getLayer(layerIdx).opacity,
                blendMode = editor.getLayer(layerIdx).blendMode,
                dstSize = this@onDraw.size.toIntSize(),
                filterQuality = FilterQuality.Companion.None
            )
        }
    }

}