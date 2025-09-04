package com.oliverastell.pixeleditor.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oliverastell.pixeleditor.util.EditorAppState

enum class Anchors {
    VeryOpen,
    Open,
    Closed
}

@Composable
fun RevealingUserPanel(
    appState: EditorAppState,
    containerDimension: Float,
    opensRightOrDown: Boolean,
    modifier: Modifier = Modifier,
    draggerContent: @Composable () -> Unit,
    settingsContent: @Composable () -> Unit
) {
    var settingsMenuHeight by remember { mutableFloatStateOf(0f) }
    var draggerDimension by remember { mutableFloatStateOf(0f) }

    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            initialValue = Anchors.Closed,
            anchors = DraggableAnchors {}
        )
    }

    val direction = if (opensRightOrDown) 1 else -1

    LaunchedEffect(containerDimension, draggerDimension) {
        anchoredDraggableState.updateAnchors(DraggableAnchors {
            Anchors.VeryOpen at direction * containerDimension + draggerDimension
            Anchors.Open at direction * containerDimension * 0.5f + draggerDimension
            Anchors.Closed at 0f
        })
    }

    LaunchedEffect(anchoredDraggableState.offset) {
        settingsMenuHeight = -anchoredDraggableState.offset
    }

    Box(
        modifier = modifier
            .onGloballyPositioned { position ->
                draggerDimension = position.size.height.toFloat()
            }
            .anchoredDraggable(anchoredDraggableState, Orientation.Vertical)
    ) {
        draggerContent()
    }

    val density = LocalDensity.current

    if (settingsMenuHeight > 0) {
        Column(
            modifier = Modifier
                .height((settingsMenuHeight / density.density).dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary)
                .verticalScroll(rememberScrollState())

        ) {
            settingsContent()
        }
    }
}