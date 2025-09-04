package com.oliverastell.pixeleditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.oliverastell.pixeleditor.ui.components.RevealingUserPanel
import com.oliverastell.pixeleditor.ui.components.DrawingCanvas
import com.oliverastell.pixeleditor.ui.components.PalettePanel
import com.oliverastell.pixeleditor.ui.components.SwitchButton
import com.oliverastell.pixeleditor.ui.components.ToolPanel
import com.oliverastell.pixeleditor.ui.components.VerticalScaffold
import com.oliverastell.pixeleditor.ui.components.ToolSwitch
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.deprecated_vector.Vec2

enum class SelectedPanel {
    PalettePanel,
    ToolPanel,
    LayerPanel
}

@Composable
fun EditorView(appState: EditorAppState, modifier: Modifier = Modifier) {
    var maxHeight by remember { mutableFloatStateOf(0f) }
    var selectedPanel by remember { mutableStateOf(SelectedPanel.ToolPanel) }

    VerticalScaffold(
        bottomNoInset = {
            when (selectedPanel) {
                SelectedPanel.PalettePanel -> PalettePanel(appState, maxHeight)
                SelectedPanel.ToolPanel -> ToolPanel(appState, maxHeight)
                SelectedPanel.LayerPanel -> TODO()
            }
        },
        bottomInset = {
            NavigationBar(
//                modifier = Modifier.height(appState.sizing.panelSelectorHeight)
            ) {
                for (panel in SelectedPanel.entries) {
                    NavigationBarItem(
                        selectedPanel == panel,
                        onClick = {
                            selectedPanel = panel
                        },
                        icon = {},
//                        modifier = Modifier.fillMaxSize()
                    )
                }
//                SelectedPanel.entries.forEachIndexed {
//
//                }
//            }
//            Row(
//                modifier = Modifier.height(appState.sizing.panelSelectorHeight)
//            ) {
//                SwitchButton (
//                    checked = selectedPanel == SelectedPanel.PalettePanel,
//                    onCheckedChange = {
//                        if (it) selectedPanel = SelectedPanel.PalettePanel
//                    },
//                    modifier = Modifier.weight(1/3f).fillMaxSize()
//                ) {}
//
//                SwitchButton (
//                    checked = selectedPanel == SelectedPanel.ToolPanel,
//                    onCheckedChange = {
//                        if (it) selectedPanel = SelectedPanel.ToolPanel
//                    },
//                    modifier = Modifier.weight(1/3f).fillMaxSize()
//                ) {}
//
//                SwitchButton (
//                    checked = selectedPanel == SelectedPanel.LayerPanel,
//                    onCheckedChange = {
//                        if (it) selectedPanel = SelectedPanel.LayerPanel
//                    },
//                    modifier = Modifier.weight(1/3f).fillMaxSize()
//                ) {}
            }
        },
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { position ->
                    maxHeight = position.size.height.toFloat()
                }
        )

        DrawingCanvas(
            appState,
            modifier = Modifier.fillMaxSize(),
            onInputBegan = { offset ->
                appState.editor.activeTool?.dragStart(appState, Vec2(offset.x, offset.y))
            },
            onInputDragged = { offset ->
                appState.editor.activeTool?.dragMoved(appState, Vec2(offset.x, offset.y))
            },
            onInputEnded = { offset ->
                appState.editor.activeTool?.dragEnded(appState, Vec2(offset.x, offset.y))
            }
        )
    }
}