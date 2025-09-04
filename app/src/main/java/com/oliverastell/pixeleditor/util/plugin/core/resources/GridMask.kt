package com.oliverastell.pixeleditor.util.plugin.core.resources

import android.graphics.BitmapShader
import android.graphics.Shader
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import com.oliverastell.pixeleditor.common.toInt
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.plugin.core.CoreIdentifiers
import com.oliverastell.pixeleditor.util.resources.Texture
import com.oliverastell.pixeleditor.util.settings.ColorSetting
import androidx.core.graphics.set
import com.oliverastell.pixeleditor.ui.components.settings.SettingsRenderer
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.settings.Setting
import com.oliverastell.pixeleditor.util.settings.WholeNumberSetting
import kotlin.random.Random

class GridMask : Texture {
    override val name = "GridMask"
    override val identifier = CoreIdentifiers.GridMask

    override val usedSettings = mutableMapOf<Identifier, Setting<*>>()

    @Composable
    override fun RenderSettings(appState: EditorAppState) {
        SettingsRenderer.ClampedWholeNumberDefault(appState, "Size", CoreIdentifiers.GridSize)
    }

    override fun getShader(appState: EditorAppState, canvasSize: IntSize): Shader {
        val setting = appState.loader.getSetting(CoreIdentifiers.GridSize) as? WholeNumberSetting
        val gridSize = setting?.get()?.toInt() ?: 1

        var bitmap = createBitmap(2, 2)
        bitmap[0, 0] = Color.Transparent.toInt()
        bitmap[0, 1] = Color.White.toInt()
        bitmap[1, 0] = Color.White.toInt()
        bitmap[1, 1] = Color.Transparent.toInt()
        bitmap = bitmap.scale(2*gridSize, 2*gridSize, false)

        return BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }
}