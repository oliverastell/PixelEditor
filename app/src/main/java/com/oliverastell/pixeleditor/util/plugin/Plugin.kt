package com.oliverastell.pixeleditor.util.plugin

import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.Namespace
import com.oliverastell.pixeleditor.util.IdentifierPath
import com.oliverastell.pixeleditor.util.plugin.DynamicObjects.dynamic
import com.oliverastell.pixeleditor.util.settings.Setting

interface Plugin {
    val loader: Loader
    val namespace: Namespace

    val settings: HashMap<IdentifierPath, Setting<*>>

    val rootModule: DynamicObject<*>

    fun getSetting(path: IdentifierPath) = settings.get(path)

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