package com.oliverastell.pixeleditor.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import com.oliverastell.pixeleditor.util.resources.Tool

@Stable
class Editor(val width: Int, val height: Int, gridSize: Int = 16) {
    val backingImage = with(Layer(width, height)) {
        for (x in 0..<width)
            for (y in 0..<height) {
                val color = if ((x/gridSize + y/gridSize) % 2 == 0)
                    Color(200, 200, 200)
                else
                    Color(150, 150, 150)
                setColorAt(x, y, color)
            }
        toImageBitmap()
    }

    var activeLayer by mutableIntStateOf(0)
    var currentTool by mutableStateOf<Tool?>(null)

    val layerCount
        get() = layers.size

    init {
        require(width > 0) { "Width must be greater than 0" }
        require(height > 0) { "Height must be greater than 0" }
    }

    private val layers = mutableStateListOf(
        Layer(width, height)
    )


    fun addLayer(): Int {
        val layer = Layer(width, height)
        layers.add(layer)
        return layers.size-1
    }

    fun setColorAt(layerIdx: Int, x: Int, y: Int, color: Color) {
        layers[layerIdx] = layers[layerIdx].withColorAt(x, y, color)
    }

    fun setOpacity(layerIdx: Int, opacity: Float) {
        layers[layerIdx] = layers[layerIdx].withOpacity(opacity)
    }

    fun setBlendMode(layerIdx: Int, blendMode: BlendMode) {
        layers[layerIdx] = layers[layerIdx].withBlendMode(blendMode)
    }

    operator fun get(layerIdx: Int) = layers[layerIdx]
}