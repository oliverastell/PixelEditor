package com.oliverastell.pixeleditor.util.core

import androidx.compose.ui.graphics.Color
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.resources.Texture
import com.oliverastell.pixeleditor.util.settings.ColorSetting
import com.oliverastell.pixeleditor.util.vector.Vec2

class SolidColor(override val loader: Loader) : Texture {
    override val name = "Solid Color"
    override val identifier = Identifier("core", "solid_color")

    override fun getColor(
        topLeft: Vec2,
        bottomRight: Vec2,
        current: Vec2
    ): Color {
        val setting = loader.getSetting(identifier) as? ColorSetting
        return setting?.get() ?: Color(0xFFFFFFFF)
    }
}