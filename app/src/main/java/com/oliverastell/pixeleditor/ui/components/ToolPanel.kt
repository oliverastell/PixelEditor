package com.oliverastell.pixeleditor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.oliverastell.pixeleditor.ui.components.settings.DividerRenderer
import com.oliverastell.pixeleditor.ui.components.settings.IconTitleRenderer
import com.oliverastell.pixeleditor.ui.painter.errorPainter
import com.oliverastell.pixeleditor.util.EditorAppState

@Composable
fun ToolPanel(appState: EditorAppState, maxHeight: Float) {
    var panelWidth by remember { mutableFloatStateOf(0f) }
    val toolsPerPage = 4

    RevealingUserPanel(
        appState = appState,
        opensRightOrDown = false,
        containerDimension = maxHeight,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .height(appState.sizing.draggerSize)
            .fillMaxWidth()
            .onGloballyPositioned { position ->
                panelWidth = position.size.width.toFloat()
            },
        draggerContent = {
            val density = LocalDensity.current

            LazyHorizontalGrid(
                rows = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(appState.editor.tools.size) { toolIdx ->
                    ToolSwitch(
                        appState,
                        tool = appState.loader.getTool(appState.editor.tools[toolIdx])!!,
                        modifier = Modifier
                            .width((panelWidth / density.density / toolsPerPage).dp)
                            .height(appState.sizing.draggerContentSize)
                    )
                }
            }
        },
        settingsContent = {
            RenderToolSettings(appState)
            DividerRenderer(appState)
            RenderTextureSettings(appState)
        }
    )
}