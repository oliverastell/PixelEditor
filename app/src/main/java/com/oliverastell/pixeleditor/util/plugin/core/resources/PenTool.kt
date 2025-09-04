package com.oliverastell.pixeleditor.util.plugin.core.resources

import androidx.compose.runtime.Composable
import com.oliverastell.pixeleditor.common.paintShaderCrisp
import com.oliverastell.pixeleditor.ui.components.settings.SettingsRenderer
import com.oliverastell.pixeleditor.util.EditorAppState
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.resources.Tool
import com.oliverastell.pixeleditor.util.settings.ColorSetting
import com.oliverastell.pixeleditor.util.settings.NumberSetting
import com.oliverastell.pixeleditor.util.settings.Setting
import com.oliverastell.pixeleditor.util.deprecated_vector.Vec2
import com.oliverastell.pixeleditor.util.plugin.core.CoreIdentifiers

class PenTool/*(override val appState: AppState)*/ : Tool {
    override val usedSettings = mutableMapOf<Identifier, Setting<*>>()

    override val name = "Pen"
    override val identifier = CoreIdentifiers.PenTool
    override val icon: Identifier = CoreIdentifiers.PenTool
    
    var previousPos = Vec2.Zero

    @Composable
    override fun RenderSettings(appState: EditorAppState) {
        SettingsRenderer.WholeNumberRanged(appState, "Pen Size", CoreIdentifiers.PenWidth, 1..20)
    }

    fun drawLine(appState: EditorAppState, start: Vec2, end: Vec2) {
        val penWidthSetting = appState.loader.getSetting(CoreIdentifiers.PenWidth) as? NumberSetting
        val penWidth = penWidthSetting!!.get() / 2

        val colorSetting = appState.loader.getSetting(CoreIdentifiers.Color) as? ColorSetting
        val color = colorSetting!!.get()

        val startCenter = start.roundToCenter()
        val endCenter = end.roundToCenter()

        val layer = appState.editor.getActiveLayer()

        layer.drawCircle(
            startCenter.x.toFloat(),
            startCenter.y.toFloat(),
            penWidth.toFloat(),
            paintShaderCrisp(appState.editor.getOrCalculateBrushShader(appState))
        )

        layer.drawLine(
            startCenter.x.toFloat(),
            startCenter.y.toFloat(),
            endCenter.x.toFloat(),
            endCenter.y.toFloat(),
            penWidth.toFloat(),
            paintShaderCrisp(appState.editor.getOrCalculateBrushShader(appState))
        )

        layer.drawCircle(
            endCenter.x.toFloat(),
            endCenter.y.toFloat(),
            penWidth.toFloat(),
            paintShaderCrisp(appState.editor.getOrCalculateBrushShader(appState))
        )
    }

    override fun dragStart(appState: EditorAppState, pos: Vec2) {
        previousPos = pos
    }

    override fun dragMoved(appState: EditorAppState, pos: Vec2) {
        drawLine(appState, previousPos, pos)
        previousPos = pos
    }

    override fun dragEnded(appState: EditorAppState, pos: Vec2) {
        drawLine(appState, previousPos, pos)
    }
}