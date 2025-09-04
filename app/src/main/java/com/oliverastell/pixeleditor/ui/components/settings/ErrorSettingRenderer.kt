package com.oliverastell.pixeleditor.ui.components.settings

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.oliverastell.pixeleditor.ui.components.ErrorText
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.Identifier

@Composable
inline fun <reified Expected> BadTypeErrorSettingRenderer(
    appState: EditorAppState,
    got: Class<*>
) {
    ErrorSettingRenderer(appState, "Cannot render ${got.simpleName} as ${Expected::class.java.simpleName}")
}

@Composable
fun UnknownErrorSettingRenderer(
    appState: EditorAppState,
    identifier: Identifier
) {
    ErrorSettingRenderer(appState, "No setting with identifier $identifier")
}

@Composable
fun ErrorSettingRenderer(
    appState: EditorAppState,
    errorMessage: String
) {
    ErrorText(
        errorMessage,
        modifier = Modifier.fillMaxSize()
    )
}