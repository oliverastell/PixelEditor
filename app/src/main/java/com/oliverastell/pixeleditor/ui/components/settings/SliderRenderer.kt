package com.oliverastell.pixeleditor.ui.components.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.oliverastell.pixeleditor.common.invlerp
import com.oliverastell.pixeleditor.common.lerp
import com.oliverastell.pixeleditor.common.toFancyString
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.settings.NumberSetting
import com.oliverastell.pixeleditor.util.settings.Setting
import com.oliverastell.pixeleditor.util.settings.WholeNumberSetting

@Composable
fun NumberSettingSliderRenderer(
    appState: EditorAppState,
    name: String,
    setting: Setting<Double>,
    range: IntRange
) = NumberSettingSliderRenderer(appState, name, setting, range.start.toDouble()..range.endInclusive.toDouble(), range.endInclusive-range.start-1)

@Composable
fun NumberSettingSliderRenderer(
    appState: EditorAppState,
    name: String,
    setting: Setting<Double>,
    range: ClosedFloatingPointRange<Double>,
    steps: Int = 0
) {
    var text by remember { mutableStateOf(setting.get().toFancyString()) }
    var slider by remember { mutableFloatStateOf(invlerp(range, setting.get()).toFloat()) }

    val valueChanged = { to: Double? ->
        setting.set(to)
        slider = invlerp(range, setting.get()).toFloat()
        text = setting.get().toFancyString()
        appState.editor.notifySettingChange()
    }

    BaseSettingRenderer(
        appState = appState,
        name = name,
        text = text,
        onTextChanged = { text = it },
        keyboardType = KeyboardType.Number,
        onDone = {
            valueChanged(text.toDoubleOrNull())
        }
    )

    Slider(
        slider,
        steps = steps,
        onValueChange = {
            valueChanged(lerp(range, it.toDouble()))
        },
        modifier = Modifier.padding(horizontal = 32.dp)
    )
}