package com.oliverastell.pixeleditor.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import com.oliverastell.pixeleditor.ui.render.EditorCanvasPainter
import com.oliverastell.pixeleditor.util.Editor

const val threshold: Float = 20F
@Composable
fun CanvasComposable(
    editor: Editor,
    modifier: Modifier = Modifier,
    onInputBegan: (Offset) -> Unit = {},
    onInputDragged: (Offset) -> Unit = {},
    onInputEnded: (Offset) -> Unit = {}
) {
    var zoom by remember { mutableFloatStateOf(1F) }
    var pan by remember { mutableStateOf(Offset(0F, 0F)) }

    // TODO: refactor the input system to be better (ie ensure all inputs are completed)
    // as it stands if you start multifinger dragging while drawing it doesnt trigger the
    // end input function

    var movement by remember { mutableFloatStateOf(0F) }
    var transforming by remember { mutableStateOf(false) }

    var topLeftOfFrameReal by remember { mutableStateOf(Offset(0F, 0F)) }
    var centerOfFrameReal by remember { mutableStateOf(Offset(0F, 0F)) }

    val oneFingerInput = oneFingerInput@{ event: PointerEvent ->
        if (transforming) {
            if (event.type == PointerEventType.Release || event.type == PointerEventType.Press)
                transforming = false
            return@oneFingerInput
        }


        val touchPosReal = event.changes.first().position
        val topLeftOfCanvasReal = Offset(
            centerOfFrameReal.x - editor.width*zoom/2 + pan.x - topLeftOfFrameReal.x,
            centerOfFrameReal.y - editor.width*zoom/2 + pan.y - topLeftOfFrameReal.y
        )

        val canvasPosition = (touchPosReal - topLeftOfCanvasReal) / zoom

        if (event.type == PointerEventType.Press)
            movement = 0F

        if (movement < threshold) {
            if (event.type == PointerEventType.Release) {
                onInputBegan(canvasPosition)
                onInputDragged(canvasPosition)
                onInputEnded(canvasPosition)
                return@oneFingerInput
            }

            movement += event.calculatePan().getDistance()
            if (movement >= threshold) {
                onInputBegan(canvasPosition)
            }
            return@oneFingerInput
        } else when (event.type) {
            PointerEventType.Move -> onInputDragged(canvasPosition)
            PointerEventType.Release -> onInputEnded(canvasPosition)
        }
    }

    val twoFingerInput = { event: PointerEvent ->
        transforming = true

        if (event.type == PointerEventType.Move) {
            val panChange = event.calculatePan()
            val zoomChange = event.calculateZoom()

            val origin = event.calculateCentroid() - centerOfFrameReal + topLeftOfFrameReal

            // Magical equation that does panning properly
            pan = (pan - origin + panChange) * zoomChange + origin
            zoom *= zoomChange
        }
    }

    ////////
    // UI //
    ////////
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .onGloballyPositioned { coordinates ->
                val size = coordinates.size

                topLeftOfFrameReal = coordinates.positionInRoot()
                centerOfFrameReal = Offset(topLeftOfFrameReal.x+size.width/2, topLeftOfFrameReal.y+size.height/2)
            }
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()

                        when (event.changes.size) {
                            1 -> oneFingerInput(event)
                            2 -> twoFingerInput(event)
                        }
                    }
                }
            }
    ) {
        Image(
            painter = EditorCanvasPainter(editor),
            contentDescription = "Canvas",
            modifier = Modifier
                .graphicsLayer(
                    scaleX = zoom,
                    scaleY = zoom,
                    translationX = pan.x,
                    translationY = pan.y
                )
                .offset()
                .align(Alignment.Center)
        )
    }

}