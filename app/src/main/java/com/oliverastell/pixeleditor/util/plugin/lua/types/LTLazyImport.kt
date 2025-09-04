package com.oliverastell.pixeleditor.util.plugin.lua.types

import com.oliverastell.pixeleditor.util.AppState
import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.nothing
import com.oliverastell.pixeleditor.util.plugin.DynamicObject
import com.oliverastell.pixeleditor.util.plugin.lua.toLua
import org.luaj.vm2.LuaValue

class LTLazyImport(val appState: AppState, val fullAccess: Boolean, val identifier: Identifier, val forceInit: Boolean) : LTDeferred() {
    val pluginsAreInitialized: Boolean
        get() = appState.loader.pluginsAreInitialized

    val module: DynamicObject<*> by lazy {
        val plugin = if (forceInit) {
            appState.loader.getOrLoadPlugin(appState, identifier.namespace)
        } else {
            if (!pluginsAreInitialized)
                return@lazy nothing { error("imported value may not yet be initialized") }

            appState.loader.getPlugin(identifier.namespace) ?: nothing { error("plugin ${identifier.namespace} doesn't exist") }
        }

        if (fullAccess) {
            plugin.getModule(identifier.path) ?: nothing { error("module ${identifier.path} doesn't exist") }
        } else {
            plugin.getLibModule(identifier.path) ?: nothing { error("lib module ${identifier.path} doesn't exist") }
        }
    }

    override val deferredTo: LuaValue by lazy {
        module.toLua()
    }

    override fun arg1(): LuaValue {
        return if (pluginsAreInitialized)
            deferredTo
        else
            this
    }

    override fun get(key: LuaValue): LuaValue {
        return if (!key.isstring() || key.tojstring().isBlank()) {
            deferredTo.get(key)
        } else {
            LTLazyImport(
                appState,
                fullAccess,
                Identifier(
                    identifier.namespace,
                    identifier.path.withSuffix(key.tojstring())
                ),
                forceInit
            )
        }
    }
}
