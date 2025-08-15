package com.oliverastell.pixeleditor.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.oliverastell.pixeleditor.util.settings.NumberSetting
import com.oliverastell.pixeleditor.util.settings.WholeNumberSetting

@Composable
fun WholeNumberSettingSliderRenderer(name: String, setting: WholeNumberSetting, range: IntRange) =
    NumberSettingSliderRenderer(name, setting, range.start.toDouble()..range.endInclusive.toDouble(), range.endInclusive-range.start)

@Composable
fun NumberSettingSliderRenderer(name: String, setting: NumberSetting, range: ClosedFloatingPointRange<Double>, steps: Int = 0) {
    var text by remember { mutableStateOf(setting.get().toString()) }

    var slider by remember { mutableFloatStateOf(0f) }

    val updateValue = remember { { to: Double? ->
        setting.set(to)
        slider = invlerp(range, setting.get()).toFloat()
        text = setting.get().toFancyString()
    } }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            Text(
                name,
                modifier = Modifier
                    .fillMaxHeight()
            )

            Spacer(modifier = Modifier.weight(1f))

            TextField(
                text,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    updateValue(text.toDoubleOrNull())
                    this.defaultKeyboardAction(ImeAction.Done)
                }),
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .fillMaxHeight()
            )
        }

        Slider(
            slider,
            steps = steps,
            onValueChange = {
                slider = it
                updateValue(lerp(range, slider.toDouble()))
            }
        )

    }

}