package com.oliverastell.pixeleditor.util

import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RuntimeShader
import android.graphics.Shader
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.core.graphics.createBitmap
import com.oliverastell.pixeleditor.common.BitmapEditor
import com.oliverastell.pixeleditor.common.toInt
import com.oliverastell.pixeleditor.util.resources.Texture
import com.oliverastell.pixeleditor.util.resources.Tool


class Editor(val width: Int, val height: Int, gridSize: Int = 16) {
    init {
        require(width > 0) { "Width must be greater than 0" }
        require(height > 0) { "Height must be greater than 0" }
    }

    val size = IntSize(width, height)

    val backingImage = run {
        val bitmap = createBitmap(width, height)
        val bitmapEditor = BitmapEditor(bitmap)

        val grey = Paint()
        grey.color = Color(150, 150, 150).toInt()
        val white = Paint()
        white.color = Color(200, 200, 200).toInt()

        val gridSize = 16
        for (x in 0..<width/gridSize)
            for (y in 0..<height/gridSize)
                bitmapEditor.drawRect(
                    Rect(
                        x * gridSize,
                        y * gridSize,
                        (x+1) * gridSize,
                        (y+1) * gridSize
                    ),
                    if ((x+y) % 2 == 0) grey else white
                )

        return@run bitmap.asImageBitmap()
    }

    var activeLayerIdx by mutableIntStateOf(0)
    var activeTool by mutableStateOf<Tool?>(null)
    var activeTextures = mutableStateListOf<Texture>()

    private var brushShader: Shader = transparentShader
    var brushValidated = false

    var tools = mutableStateListOf<Identifier>()
    var textures = mutableStateListOf<Identifier>()

    // Expensive
    fun calculateBrushShader(appState: EditorAppState) {
        brushValidated = true
        brushShader = calculateBrushShader(appState, activeTextures, size)
    }

    fun getOrCalculateBrushShader(appState: EditorAppState): Shader {
        if (!brushValidated)
            calculateBrushShader(appState)
        return brushShader
    }

    fun invalidateBrushShader() {
        brushValidated = false
    }

    val layerCount
        get() = layers.size
    val layers = mutableListOf(Layer(this))

    fun getLayer(layerIdx: Int) = layers[layerIdx]
    fun getActiveLayer() = layers[activeLayerIdx]

    var changed by mutableStateOf(Unit, neverEqualPolicy())
    fun notifyCanvasChange() {
        changed = Unit
    }

    fun notifySettingChange() {
        invalidateBrushShader()
    }
}