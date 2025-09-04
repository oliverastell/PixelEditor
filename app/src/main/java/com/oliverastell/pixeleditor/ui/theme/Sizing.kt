package com.oliverastell.pixeleditor.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Immutable
data class Sizing(
    // short for pixel size
    val s: Dp = 3.dp,

    val draggerSize: Dp = s*32,
    val draggerContentSize: Dp = s*16,
    val draggerContentIconSize: Dp = s*12,

    val baseSettingHeight: Dp = s*16,
    val baseSettingIconHeight: Dp = s*12,

    val panelSelectorHeight: Dp = s*16,

    val textureIconResolution: IntSize = IntSize(32, 32),

    ) {
    companion object {
        val default = Sizing()
    }
}