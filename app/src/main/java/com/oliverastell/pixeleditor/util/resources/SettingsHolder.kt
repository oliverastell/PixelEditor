package com.oliverastell.pixeleditor.util.resources

import androidx.compose.runtime.Composable
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.settings.Setting

interface SettingsHolder {
    val usedSettings: MutableMap<Identifier, Setting<*>>//List<Setting<*>>

//    fun Loader.getLocalSetting(identifier: Identifier): Setting<*>? {
//        val setting = localSettings[identifier]
//        if (setting == null)
//            return this.getSetting(identifier)
//        return setting
//    }

    @Composable
    fun RenderSettings(appState: EditorAppState) {}
}