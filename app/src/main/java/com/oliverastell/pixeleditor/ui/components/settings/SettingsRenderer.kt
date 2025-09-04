package com.oliverastell.pixeleditor.ui.components.settings

import androidx.compose.runtime.Composable
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.settings.ClampedNumberSetting
import com.oliverastell.pixeleditor.util.settings.ClampedWholeNumberSetting
import com.oliverastell.pixeleditor.util.settings.ColorSetting
import com.oliverastell.pixeleditor.util.settings.NumberSetting


object SettingsRenderer {
    @Composable
    private inline fun <reified SettingType> withSetting(appState: EditorAppState, settingIdentifier: Identifier, renderer: @Composable (setting: SettingType) -> Unit) {
        val setting = appState.loader.getSetting(settingIdentifier) ?: run {
            UnknownErrorSettingRenderer(appState, settingIdentifier)
            return
        }

        if (setting !is SettingType) {
            BadTypeErrorSettingRenderer<SettingType>(appState, setting.javaClass)
            return
        }

        renderer(setting)
    }

    @Composable
    fun NumberDefault(appState: EditorAppState, name: String, settingIdentifier: Identifier) {
        withSetting<NumberSetting>(appState, settingIdentifier) { setting ->
            NumberSettingRenderer(appState, name, setting)
        }
    }

    @Composable
    fun NumberRanged(appState: EditorAppState, name: String, settingIdentifier: Identifier, range: ClosedFloatingPointRange<Double>) {
        withSetting<NumberSetting>(appState, settingIdentifier) { setting ->
            NumberSettingSliderRenderer(appState, name, setting, range)
        }
    }

    @Composable
    fun WholeNumberDefault(appState: EditorAppState, name: String, settingIdentifier: Identifier) =
        NumberDefault(appState, name, settingIdentifier)

    @Composable
    fun WholeNumberRanged(appState: EditorAppState, name: String, settingIdentifier: Identifier, range: IntRange) {
        withSetting<NumberSetting>(appState, settingIdentifier) { setting ->
            NumberSettingSliderRenderer(appState, name, setting, range)
        }
    }

    @Composable
    fun ClampedNumberDefault(appState: EditorAppState, name: String, settingIdentifier: Identifier) {
        withSetting<ClampedNumberSetting>(appState, settingIdentifier) { setting ->
            NumberSettingSliderRenderer(appState, name, setting, setting.range)
        }
    }

    @Composable
    fun ClampedWholeNumberDefault(appState: EditorAppState, name: String, settingIdentifier: Identifier) {
        withSetting<ClampedWholeNumberSetting>(appState, settingIdentifier) { setting ->
            NumberSettingSliderRenderer(appState, name, setting, setting.intRange)
        }
    }

    @Composable
    fun ColorDefault(appState: EditorAppState, name: String, settingIdentifier: Identifier) {
        withSetting<ColorSetting>(appState, settingIdentifier) { setting ->
            ExpandedColorSettingRenderer(appState, name, setting)
        }
    }

}