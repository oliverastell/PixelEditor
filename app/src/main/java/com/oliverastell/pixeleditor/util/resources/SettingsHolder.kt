package com.oliverastell.pixeleditor.util.resources

import androidx.compose.runtime.Composable
import com.oliverastell.pixeleditor.util.settings.Setting

interface SettingsHolder {
    val trackedSettings: List<Setting<*>>

    @Composable
    fun RenderSettings() {}
}