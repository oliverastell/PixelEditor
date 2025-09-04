package com.oliverastell.pixeleditor.util.resources

import android.graphics.Shader
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.oliverastell.pixeleditor.util.AppState
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.deprecated_vector.Vec2

interface Texture : Resource, SettingsHolder {
    fun getShader(appState: EditorAppState, canvasSize: IntSize): Shader
}