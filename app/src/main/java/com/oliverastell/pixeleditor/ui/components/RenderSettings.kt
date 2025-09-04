package com.oliverastell.pixeleditor.ui.components

import androidx.compose.runtime.Composable
import com.oliverastell.pixeleditor.ui.components.settings.IconTitleRenderer
import com.oliverastell.pixeleditor.ui.painter.ShaderPainter
import com.oliverastell.pixeleditor.ui.painter.errorPainter
import com.oliverastell.pixeleditor.util.EditorAppState

@Composable
fun RenderToolSettings(appState: EditorAppState) {
    val tool = appState.editor.activeTool
    if (tool == null)
        return

    IconTitleRenderer(
        appState,
        painter = appState.loader.getVisual(tool.icon)?.painter() ?: errorPainter,
        title = tool.name
    )
    tool.RenderSettings(appState)
}

@Composable
fun RenderTextureSettings(appState: EditorAppState) {
    for (texture in appState.editor.activeTextures) {
        IconTitleRenderer(
            appState,
            painter = ShaderPainter(
                texture.getShader(
                    appState, appState.sizing.textureIconResolution
                ),
                appState.sizing.textureIconResolution
            ),
            title = texture.name
        )
        texture.RenderSettings(appState)
    }
}