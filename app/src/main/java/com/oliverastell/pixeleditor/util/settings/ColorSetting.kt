package com.oliverastell.pixeleditor.util.settings

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

interface ColorSetting : Setting<Color> {
    override fun convert(value: Any): Color? {
        if (value is Color)
            return value

        if (value is String)
            return try {
                Color(value.toColorInt())
            } catch (_: IllegalArgumentException) {
                Color(value.toLongOrNull() ?: return null)
            }

        return null
    }
}