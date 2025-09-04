package com.oliverastell.pixeleditor.util.plugin.core

import androidx.compose.ui.graphics.Color
import com.oliverastell.pixeleditor.R
import com.oliverastell.pixeleditor.util.AppState
import com.oliverastell.pixeleditor.util.Namespace
import com.oliverastell.pixeleditor.util.IdentifierPath
import com.oliverastell.pixeleditor.util.Visual
import com.oliverastell.pixeleditor.util.plugin.core.resources.PenTool
import com.oliverastell.pixeleditor.util.plugin.DynamicObject
import com.oliverastell.pixeleditor.util.plugin.DynamicObjects.dynamic
import com.oliverastell.pixeleditor.util.plugin.DynamicPackageObject
import com.oliverastell.pixeleditor.util.plugin.Plugin
import com.oliverastell.pixeleditor.util.plugin.core.resources.GridMask
import com.oliverastell.pixeleditor.util.plugin.core.resources.SolidColor
import com.oliverastell.pixeleditor.util.resources.Texture
import com.oliverastell.pixeleditor.util.resources.Tool
import com.oliverastell.pixeleditor.util.settings.Setting

class CorePlugin(override val appState: AppState) : Plugin {
    override val namespace = Namespace.fromString("core")

    override val settings = mutableMapOf<IdentifierPath, Setting<*>>(
        CoreIdentifiers.PenWidth.path to Setting.clampedNumber(1.0, 1.0..20.0),
        CoreIdentifiers.Color.path to Setting.color(Color.White),
        CoreIdentifiers.GridSize.path to Setting.clampedWholeNumber(1, 1..16)
    )
    override val visuals = mutableMapOf<IdentifierPath, Visual>(
        CoreIdentifiers.PenTool.path to Visual.from(R.drawable.pencil),
    )
    override val tools = mutableMapOf<IdentifierPath, Tool>(
        CoreIdentifiers.PenTool.path to PenTool()
    )
    override val textures = mutableMapOf<IdentifierPath, Texture>(
        CoreIdentifiers.SolidColorTexture.path to SolidColor(),
        CoreIdentifiers.GridMask.path to GridMask()
    )


    override val rootModule: DynamicObject<*> = DynamicPackageObject().apply {
        set("coolprint", { args: List<Any> ->
            println("WOW SUPER COOL! $args")
        }.dynamic())
    }

    override fun getLibModule(path: IdentifierPath): DynamicObject<*>? = getModule(path)

    override fun initialize() {}
}