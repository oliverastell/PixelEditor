package com.oliverastell.pixeleditor.util

import android.graphics.ImageDecoder
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import com.google.accompanist.drawablepainter.DrawablePainter
import java.io.IOException
import java.nio.file.Path

interface Visual {
    @Composable
    fun painter(): Painter

    companion object {
        fun from(path: Path): Visual? {
            val source = ImageDecoder.createSource(path.toFile())

            return try {
                DrawableVisual(ImageDecoder.decodeDrawable(source).apply {
                    this.isFilterBitmap = false
                })
            } catch (_: IOException) {
                null
            }
        }

        fun from(@DrawableRes id: Int): Visual {
            return ResourceVisual(id)
        }
    }
}

data class DrawableVisual(val drawable: Drawable) : Visual {
    @Composable
    override fun painter(): Painter {
        return DrawablePainter(drawable)
    }
}

data class ResourceVisual(@param:DrawableRes val id: Int) : Visual {
    @Composable
    override fun painter(): Painter {
        val drawable = ContextCompat.getDrawable(LocalContext.current, id)!!
        drawable.isFilterBitmap = false
        return DrawablePainter(drawable)
    }
}