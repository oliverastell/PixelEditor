package com.oliverastell.pixeleditor.util.core

import androidx.compose.runtime.Composable
import com.oliverastell.pixeleditor.ui.settings.SettingsRenderer
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.resources.Tool
import com.oliverastell.pixeleditor.util.selection.Selection
import com.oliverastell.pixeleditor.util.selection.Shapes
import com.oliverastell.pixeleditor.util.settings.ColorSetting
import com.oliverastell.pixeleditor.util.settings.NumberSetting
import com.oliverastell.pixeleditor.util.settings.Setting
import com.oliverastell.pixeleditor.util.vector.Vec2

class PenTool(override val loader: Loader) : Tool {
    override val trackedSettings = listOf<Setting<*>>()


    override val name = "Pen"
    override val identifier = Identifier("core", "pen")

    var previousPos = Vec2.Zero
    var selection = Selection(loader.editor!!)

    @Composable
    override fun RenderSettings() {
        SettingsRenderer.ClampedNumberDefault(loader, "Pen Size", CoreIdentifiers.PenWidth)
        SettingsRenderer.ClampedWholeNumberDefault(loader, "Pen Size", CoreIdentifiers.PenWidth)
        SettingsRenderer.ColorDefault(loader, "Color", CoreIdentifiers.Color)
        SettingsRenderer.ColorDefault(loader, "Color", CoreIdentifiers.Color)
    }

    fun drawLine(start: Vec2, end: Vec2) {
        val penWidthSetting = loader.getSetting(CoreIdentifiers.PenWidth) as? NumberSetting

        val penWidth = penWidthSetting!!.get() / 2

        val startCenter = start.roundToCenter()
        val endCenter = end.roundToCenter()

        val canvas = loader.editor!!

        val circle1 = Shapes.circle(startCenter, penWidth)
        selection.add(circle1.toBitmask(canvas))

        val line = Shapes.line(startCenter,endCenter, penWidth)
        selection.add(line.toBitmask(canvas))

        val circle2 = Shapes.circle(endCenter, penWidth)
        selection.add(circle2.toBitmask(canvas))
    }

    override fun dragStart(pos: Vec2) {
        selection = Selection(loader.editor!!)
        previousPos = pos
    }

    override fun dragMoved(pos: Vec2) {
        drawLine(previousPos, pos)
        previousPos = pos
    }

    override fun dragEnded(pos: Vec2) {
        drawLine(previousPos, pos)


        val colorSetting = loader.getSetting(CoreIdentifiers.Color) as? ColorSetting
        val color = colorSetting!!.get()

        val canvas = loader.editor!!
        selection.forEachPixel { x, y ->
            if (x in 0..<canvas.width && y in 0..<canvas.height)
                canvas.setColorAt(canvas.activeLayer, x, y, color)
        }
    }
}