package com.oliverastell.pixeleditor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

const val panelHeight = 100

enum class Anchors {
    VeryOpen,
    Open,
    Closed
}

@Composable
fun GenericPanel(menu: @Composable () -> Unit, subMenu: @Composable () -> Unit) {
    var screenHeight by remember { mutableFloatStateOf(0f) }
    var draggerHeight by remember { mutableFloatStateOf(0f) }

    val dragState = remember { AnchoredDraggableState(
        initialValue = Anchors.Closed,
        anchors = DraggableAnchors {}
    ) }

    val density = LocalDensity.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { position ->
                screenHeight = position.size.height.toFloat()
            }
    ) {
        LaunchedEffect(screenHeight, draggerHeight) {
            dragState.updateAnchors(DraggableAnchors {
                Anchors.VeryOpen at -screenHeight + draggerHeight
                Anchors.Open at -screenHeight * 0.5f + draggerHeight
                Anchors.Closed at 0f
            })
        }
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(panelHeight.dp)
                .onGloballyPositioned { position ->
                    draggerHeight = position.size.height.toFloat()
                }
                .anchoredDraggable(dragState, Orientation.Vertical)
                .background(MaterialTheme.colorScheme.primary)
        ) {
            menu()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(0.dp, (-dragState.offset / density.density).dp)
                .background(MaterialTheme.colorScheme.secondary)
                .verticalScroll(rememberScrollState())
        ) {
            if (dragState.offset < 0)
                subMenu()
        }
    }
}