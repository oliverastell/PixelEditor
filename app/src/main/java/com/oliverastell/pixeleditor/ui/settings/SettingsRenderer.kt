package com.oliverastell.pixeleditor.ui.settings

import androidx.compose.runtime.Composable
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.settings.ClampedNumberSetting
import com.oliverastell.pixeleditor.util.settings.ClampedWholeNumberSetting
import com.oliverastell.pixeleditor.util.settings.ColorSetting
import com.oliverastell.pixeleditor.util.settings.NumberSetting


object SettingsRenderer {
    @Composable
    private inline fun <reified SettingType> withSetting(loader: Loader, settingIdentifier: Identifier, renderer: @Composable (setting: SettingType) -> Unit) {
        val setting = loader.getSetting(settingIdentifier) ?: run {
            UnknownErrorSettingRenderer(settingIdentifier)
            return
        }

        if (setting !is SettingType) {
            BadTypeErrorSettingRenderer<SettingType>(setting.javaClass)
            return
        }

        renderer(setting)
    }

    @Composable
    fun NumberDefault(loader: Loader, name: String, settingIdentifier: Identifier) {
        withSetting<NumberSetting>(loader, settingIdentifier) { setting ->
            NumberSettingRenderer(name, setting)
        }
    }

    @Composable
    fun WholeNumberDefault(loader: Loader, name: String, settingIdentifier: Identifier) = NumberDefault(loader, name, settingIdentifier)

    @Composable
    fun ClampedNumberDefault(loader: Loader, name: String, settingIdentifier: Identifier) {
        withSetting<ClampedNumberSetting>(loader, settingIdentifier) { setting ->
            NumberSettingSliderRenderer(name, setting, setting.range)
        }
    }

    @Composable
    fun ClampedWholeNumberDefault(loader: Loader, name: String, settingIdentifier: Identifier) {
        withSetting<ClampedWholeNumberSetting>(loader, settingIdentifier) { setting ->
            WholeNumberSettingSliderRenderer(name, setting, setting.intRange)
        }
    }


//    @Composable
//    fun ClampedIntDefault(loader: Loader, name: String, settingIdentifier: Identifier) {
//        withSetting<ClampedNumberSetting>(loader, settingIdentifier) { setting ->
//            SliderIntSettingRenderer(name, setting, setting.range)
//        }
//    }
//
//    @Composable
//    fun ClampedFloatDefault(loader: Loader, name: String, settingIdentifier: Identifier) {
//        withSetting<ClampedFloatSetting>(loader, settingIdentifier) { setting ->
//            SliderFloatSettingRenderer(name, setting, setting.range)
//        }
//    }

    @Composable
    fun ColorDefault(loader: Loader, name: String, settingIdentifier: Identifier) {
        withSetting<ColorSetting>(loader, settingIdentifier) { setting ->
            ExpandedColorSettingRenderer(name, setting)
        }
    }

}