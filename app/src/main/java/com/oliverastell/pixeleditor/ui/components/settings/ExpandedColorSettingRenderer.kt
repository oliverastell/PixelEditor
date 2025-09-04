package com.oliverastell.pixeleditor.ui.components.settings

import android.graphics.RuntimeShader
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.oliverastell.pixeleditor.common.hexToColor
import com.oliverastell.pixeleditor.common.toHexString
import com.oliverastell.pixeleditor.common.toInt
import com.oliverastell.pixeleditor.common.toUInt
import com.oliverastell.pixeleditor.ui.components.HSVWheel
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.settings.Setting

private fun getHSV(color: Color): FloatArray {
    val hsv = FloatArray(3).apply {
        android.graphics.Color.colorToHSV(color.toInt(), this)
    }
    return hsv
}


@Composable
fun ExpandedColorSettingRenderer(
    appState: EditorAppState,
    name: String,
    setting: Setting<Color>
) {
    var text by remember { mutableStateOf(setting.get().toHexString()) }

    val hsv = getHSV(setting.get())
    var hue by remember { mutableFloatStateOf(hsv[0]) }
    var saturation by remember { mutableFloatStateOf(hsv[1]) }
    var value by remember { mutableFloatStateOf(hsv[2]) }

    val valueChanged = { to: Color? ->
        setting.set(to)
        text = setting.get().toHexString()
        appState.editor.notifySettingChange()
    }

    val updateWheel = updateWheel@{ to: Color? ->
        if (to == null)
            return@updateWheel
        val hsv = getHSV(to)
        hue = hsv[0]
        saturation = hsv[1]
        value = hsv[2]
    }

    BaseSettingRenderer(
        appState = appState,
        name = name,
        text = text,
        onTextChanged = { text = it },
        keyboardType = KeyboardType.Text,
        onDone = {
            val color = try {
                text.hexToColor()
            } catch (_: IllegalArgumentException) {
                null
            }
            valueChanged(color)
            updateWheel(color)
        }
    )

    HSVWheel(
        hue,
        saturation,
        value,
        padding = PaddingValues(20.dp, 10.dp),
        onHueChange = { newHue ->
            hue = newHue
            valueChanged(Color.hsv(hue, saturation, value))
        },
        onSVChange = { newSaturation, newValue ->
            saturation = newSaturation
            value = newValue
            valueChanged(Color.hsv(hue, saturation, value))
        },
        modifier = Modifier.fillMaxWidth(.75f)
    )
}