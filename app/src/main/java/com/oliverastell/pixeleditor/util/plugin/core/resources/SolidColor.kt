package com.oliverastell.pixeleditor.util.plugin.core.resources

import android.graphics.BitmapShader
import android.graphics.Shader
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.createBitmap
import com.oliverastell.pixeleditor.common.toInt
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.plugin.core.CoreIdentifiers
import com.oliverastell.pixeleditor.util.resources.Texture
import com.oliverastell.pixeleditor.util.settings.ColorSetting
import androidx.core.graphics.set
import com.oliverastell.pixeleditor.ui.components.settings.SettingsRenderer
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.settings.Setting

class SolidColor : Texture {
    override val name = "Solid Color"
    override val identifier = CoreIdentifiers.SolidColorTexture

    override val usedSettings = mutableMapOf<Identifier, Setting<*>>()

    @Composable
    override fun RenderSettings(appState: EditorAppState) {
        SettingsRenderer.ColorDefault(appState, "Color", CoreIdentifiers.Color)
    }

    override fun getShader(appState: EditorAppState, canvasSize: IntSize): Shader {
        val setting = appState.loader.getSetting(CoreIdentifiers.Color) as? ColorSetting
        val color = setting?.get() ?: Color(0x00000000)

        val bitmap = createBitmap(1, 1)
        bitmap[0, 0] = color.toInt()

        return BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
    }
}