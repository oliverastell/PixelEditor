package com.oliverastell.pixeleditor.ui.color

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin


val rgbGradientBrush = Brush.sweepGradient(
    listOf(
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Cyan,
        Color.Blue,
        Color.Magenta,
        Color.Red,
    )
)

val saturationBrush = Brush.horizontalGradient(
    listOf(
        Color.White,
        Color.Transparent
    )
)

val valueBrush = Brush.verticalGradient(
    listOf(
        Color.Transparent,
        Color.Black
    )
)

@Composable
fun HSVWheel(
    hue: Float,
    saturation: Float,
    value: Float,
    onHueChange: (hue: Float) -> Unit,
    onSVChange: (saturation: Float, value: Float) -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp)
) {
    val innerBoxFraction = 0.5f
    val strokeFraction = 1/12f

    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .pointerInput(Unit) {
                val diameter = size.width.toFloat()

                fun event(position: Offset) {
                    val fraction = position / diameter
                    onHueChange(
                        (atan2(fraction.y - 0.5f, fraction.x - 0.5f) / PI * 180).toFloat().mod(360f)
                    )
                }

                detectDragGestures(
                    onDragStart = { position ->
                        event(position)
                    },
                    onDrag = { change, dragAmount ->
                        event(change.position)
                    }
                )
            }
            .padding(padding)
            .aspectRatio(1f)
    ) {
        // Wheel (outer)
        Canvas(modifier = Modifier.fillMaxSize()) {
            val diameter = this.size.minDimension
            val stroke = diameter * strokeFraction

            val trueRadius = diameter / 2
            val radiusStrokeOffset = (diameter - stroke) / 2

            drawCircle(
                rgbGradientBrush,
                radius = radiusStrokeOffset,
                style = Stroke(stroke)
            )

            // Selector
            val radians = hue / 180f * PI

            drawCircle(
                Color.White,
                radius = stroke / 2,
                center = Offset(
                    cos(radians).toFloat() * radiusStrokeOffset + trueRadius,
                    sin(radians).toFloat() * radiusStrokeOffset + trueRadius
                )
            )
        }

        // Square
        Canvas(
            modifier = Modifier
                .fillMaxSize(innerBoxFraction)
                .align(Alignment.Center)
                .pointerInput(Unit) {
                    val diameter = size.width.toFloat()

                    fun event(position: Offset) {
                        val fraction = position / diameter
                        onSVChange(
                            fraction.x.coerceIn(0f..1f),
                            (1-fraction.y).coerceIn(0f..1f)
                        )
                    }

                    detectDragGestures(
                        onDragStart = { position ->
                            event(position)
                        },
                        onDrag = { change, dragAmount ->
                            event(change.position)
                        }
                    )
                }
        ) {
            val outerDiameter = this.size.minDimension / innerBoxFraction
            val stroke = outerDiameter * strokeFraction

            val diameter = this.size.minDimension
            val cornerRadius = diameter / 24

            drawRoundRect(
                Color.hsv(hue, 1f, 1f),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
            drawRoundRect(
                saturationBrush,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
            drawRoundRect(
                valueBrush,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )

            // Selector
            drawCircle(
                Color.White,
                radius = stroke / 2,
                center = Offset(
                    diameter * saturation,
                    diameter * (1-value)
                )
            )
        }
    }
}