package com.oliverastell.pixeleditor.util

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.oliverastell.pixeleditor.ui.theme.Sizing

@Stable
class AppState(
    val sizing: Sizing,
    val loader: Loader,
    val fileSystem: FileSystem,
    editor: Editor?
) {
    var editor by mutableStateOf(editor)

    fun editorAppState() = EditorAppState(this)
}

@Immutable
data class EditorAppState(val appState: AppState) {
    val sizing: Sizing
        get() = appState.sizing

    val loader: Loader
        get() = appState.loader

    val editor: Editor
        get() = appState.editor!!
}