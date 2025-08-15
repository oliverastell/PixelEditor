package com.oliverastell.pixeleditor.ui.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.oliverastell.pixeleditor.ui.color.HSVWheel
import com.oliverastell.pixeleditor.util.settings.Setting

@Composable
fun ExpandedColorSettingRenderer(name: String, setting: Setting<Color>) {
    val color = setting.get()
    val hsl = FloatArray(3)
    ColorUtils.RGBToHSL(
        (color.red * 256).toInt(),
        (color.green * 256).toInt(),
        (color.blue * 256).toInt(),
        hsl
    )

    var hue by remember { mutableFloatStateOf(hsl[0]) }
    var saturation by remember { mutableFloatStateOf(hsl[1]) }
    var value by remember { mutableFloatStateOf(hsl[2]) }

    val updateColor = remember { {
        setting.set(Color.hsv(hue, saturation, value))
    } }

    HSVWheel(
        hue,
        saturation,
        value,
        padding = PaddingValues(20.dp, 10.dp),
        onHueChange = { newHue ->
            hue = newHue
            updateColor()
        },
        onSVChange = { newSaturation, newValue ->
            saturation = newSaturation
            value = newValue
            updateColor()
        },
        modifier = Modifier.fillMaxWidth(.75f)
    )
//    Slider
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(100.dp)
//    ) {
//        Text(
//            name,
//            modifier = Modifier
//                .fillMaxHeight()
//        )
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        TextField(
//            text,
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(
//                keyboardType = KeyboardType.Number,
//                imeAction = ImeAction.Done
//            ),
//            keyboardActions = KeyboardActions(onDone = {
//                setting.set(text.toIntOrNull())
//                text = setting.get().toString()
//                this.defaultKeyboardAction(ImeAction.Done)
//            }),
//            onValueChange = {
//                text = it
//            },
//            modifier = Modifier
//                .fillMaxHeight()
//        )
//    }
}