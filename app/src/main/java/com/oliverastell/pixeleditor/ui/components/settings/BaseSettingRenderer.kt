package com.oliverastell.pixeleditor.ui.components.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.oliverastell.pixeleditor.util.EditorAppState

@Composable
fun BaseSettingRenderer(
    appState: EditorAppState,
    name: String,
    text: String,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    onTextChanged: (String) -> Unit,
    onDone: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(appState.sizing.baseSettingHeight)
    ) {
        val valueChanged = { to: String ->
            onTextChanged(to)
            appState.editor.notifySettingChange()
        }

        Text(
            name,
            textAlign = TextAlign.Left,
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentHeight()
        )

        Spacer(modifier = Modifier.weight(1f))

        TextField(
            text,
            singleLine = true,
            textStyle = TextStyle(fontSize = TextUnit.Unspecified),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                onDone()
                this.defaultKeyboardAction(ImeAction.Done)
            }),
            onValueChange = valueChanged,
            modifier = Modifier
                .fillMaxHeight()
        )
    }
}