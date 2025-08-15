package com.oliverastell.pixeleditor.util.plugin.lua.libs

import com.oliverastell.pixeleditor.util.Loader
import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.plugin.DynamicObjects.dynamic
import com.oliverastell.pixeleditor.util.plugin.FilePlugin
import com.oliverastell.pixeleditor.util.plugin.Plugin
import com.oliverastell.pixeleditor.util.plugin.lua.helper.FunctionHelper
import com.oliverastell.pixeleditor.util.plugin.lua.toLua
import com.oliverastell.pixeleditor.util.plugin.lua.types.LTLazyImport
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.TwoArgFunction

class PluginLib(val plugin: Plugin) : TwoArgFunction() {
    override fun call(
        modname: LuaValue,
        env: LuaValue
    ): LuaValue {
        val globals = env.checkglobals()

        globals.set("loader", plugin.loader.dynamic().toLua())
        globals.set("plugin", plugin.dynamic().toLua())
        globals.set("import", import)
        globals.set("require", NIL)

        return env
    }

    val import = FunctionHelper.function { args ->
        val modulePath = args.checkstring(1).tojstring()
        val forceInit = args.optboolean(2, false)

        val identifier = Identifier.fromString(plugin.namespace, modulePath)
        val fullAccess = plugin.namespace == identifier.namespace

        LTLazyImport(plugin.loader, fullAccess, identifier, forceInit)
    }
}