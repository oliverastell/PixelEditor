package com.oliverastell.pixeleditor.util.plugin

import android.graphics.drawable.Drawable
import com.oliverastell.pixeleditor.util.AppState
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.Namespace
import com.oliverastell.pixeleditor.util.IdentifierPath
import com.oliverastell.pixeleditor.util.Visual
import com.oliverastell.pixeleditor.util.plugin.DynamicObjects.dynamic
import com.oliverastell.pixeleditor.util.resources.Texture
import com.oliverastell.pixeleditor.util.resources.Tool
import com.oliverastell.pixeleditor.util.settings.Setting

interface Plugin {
    val appState: AppState
    val namespace: Namespace

    val settings: MutableMap<IdentifierPath, Setting<*>>
    val visuals: MutableMap<IdentifierPath, Visual>
    val tools: MutableMap<IdentifierPath, Tool>
    val textures: MutableMap<IdentifierPath, Texture>

    val rootModule: DynamicObject<*>

    fun getVisual(path: IdentifierPath) = visuals[path]
    fun getSetting(path: IdentifierPath) = settings[path]
    fun getTool(path: IdentifierPath) = tools[path]
    fun getTexture(path: IdentifierPath) = textures[path]

    fun getModule(path: IdentifierPath): DynamicObject<*>? {
        var currentModule = rootModule

        for (sub in path.segments) {
            currentModule = currentModule[sub.dynamic()]
        }

        return currentModule
    }

    fun getLibModule(path: IdentifierPath): DynamicObject<*>?

    fun initialize()
}