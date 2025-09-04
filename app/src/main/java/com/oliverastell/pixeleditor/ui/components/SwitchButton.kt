package com.oliverastell.pixeleditor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.selection.toggleable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role

@Composable
fun SwitchButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,

    checkedColor: Color = Color.Green,
    uncheckedColor: Color = Color.Red,

    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .toggleable(
                checked,
                role = Role.Switch,
                onValueChange = onCheckedChange,
            )
    ) {
        Box(
            modifier = Modifier
                .background(if (checked) checkedColor else uncheckedColor)
                .fillMaxSize()
        ) {
            content()
        }
    }
}