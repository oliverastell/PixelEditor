package com.oliverastell.pixeleditor.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import com.oliverastell.pixeleditor.ui.painter.errorPainter
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.resources.Tool

@Composable
fun ToolSwitch(
    appState: EditorAppState,
    tool: Tool,
    modifier: Modifier = Modifier
) {
    val visual = appState.loader.getVisual(tool.icon)

    val painter = visual?.painter() ?: errorPainter

    val checked = appState.editor.activeTool == tool

    SwitchButton(
        checked,
        onCheckedChange = {
            if (it)
                appState.editor.activeTool = tool
            else if (checked)
                appState.editor.activeTool = null
        },
        modifier = modifier
    ) {
        Image(
            painter,
            contentDescription = tool.name,
            modifier = Modifier
                .align(Alignment.Center)
                .size(appState.sizing.draggerContentIconSize)
        )
    }
}