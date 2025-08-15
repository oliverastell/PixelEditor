package com.oliverastell.pixeleditor.util.plugin.lua

import com.oliverastell.pixeleditor.util.plugin.lua.libs.PluginLib
import com.oliverastell.pixeleditor.util.plugin.lua.libs.TimeLib
import com.oliverastell.pixeleditor.util.plugin.lua.types.LTReadOnlyTable
import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaString
import org.luaj.vm2.Varargs
import org.luaj.vm2.compiler.LuaC
import org.luaj.vm2.lib.Bit32Lib
import org.luaj.vm2.lib.PackageLib
import org.luaj.vm2.lib.StringLib
import org.luaj.vm2.lib.TableLib
import org.luaj.vm2.lib.jse.JseBaseLib
import org.luaj.vm2.lib.jse.JseMathLib


object LuaServer {
    val globals = Globals()

    init {
        globals.load(JseBaseLib())
        globals.load(PackageLib())
        globals.load(StringLib())
        globals.load(JseMathLib())

        LoadState.install(globals)
        LuaC.install(globals)

        LuaString.s_metatable = LTReadOnlyTable(LuaString.s_metatable)
    }

    fun runSandbox(pluginLib: PluginLib, source: String, sourceName: String): Varargs {
        val sandboxedGlobals = Globals()
        sandboxedGlobals.load(JseBaseLib())
        sandboxedGlobals.load(PackageLib())
        sandboxedGlobals.load(Bit32Lib())
        sandboxedGlobals.load(TableLib())
        sandboxedGlobals.load(StringLib())
        sandboxedGlobals.load(JseMathLib())
        sandboxedGlobals.load(TimeLib())
        sandboxedGlobals.load(pluginLib)


        val chunk = globals.load(source, sourceName, sandboxedGlobals)

        return chunk.invoke()
    }
}