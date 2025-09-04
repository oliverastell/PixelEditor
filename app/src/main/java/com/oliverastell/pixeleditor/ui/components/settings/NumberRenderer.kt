package com.oliverastell.pixeleditor.ui.components.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.oliverastell.pixeleditor.common.invlerp
import com.oliverastell.pixeleditor.common.toFancyString
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.settings.NumberSetting
import com.oliverastell.pixeleditor.util.settings.Setting

@Composable
fun NumberSettingRenderer(
    appState: EditorAppState,
    name: String,
    setting: Setting<Double>
) {
    var text by remember { mutableStateOf(setting.get().toString()) }

    val valueChanged = { to: Double? ->
        setting.set(to)
        text = setting.get().toFancyString()
    }

    BaseSettingRenderer(
        appState = appState,
        name = name,
        text = text,
        onTextChanged = { text = it },
        keyboardType = KeyboardType.Decimal,
        onDone = {
            valueChanged(text.toDoubleOrNull())
        }
    )

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
//                updateValue(text.toDoubleOrNull())
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