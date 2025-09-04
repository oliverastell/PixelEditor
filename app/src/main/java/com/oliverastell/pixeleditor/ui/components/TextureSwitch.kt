package com.oliverastell.pixeleditor.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.oliverastell.pixeleditor.ui.painter.ShaderPainter
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.resources.Texture

@Composable
fun TextureSwitch(
    appState: EditorAppState,
    texture: Texture,
    modifier: Modifier = Modifier
) {
    val painter = ShaderPainter(
        texture.getShader(
            appState, appState.sizing.textureIconResolution
        ),
        appState.sizing.textureIconResolution
    )

    val checked = appState.editor.activeTextures.contains(texture)

    SwitchButton(
        checked,
        onCheckedChange = {
            if (it)
                appState.editor.activeTextures.add(texture)
            else if (checked)
                appState.editor.activeTextures.remove(texture)
            appState.editor.invalidateBrushShader()
        },
        modifier = modifier
    ) {
        Image(
            painter,
            contentDescription = texture.name,
            modifier = Modifier
                .align(Alignment.Center)
                .size(appState.sizing.draggerContentIconSize)
        )
    }
}