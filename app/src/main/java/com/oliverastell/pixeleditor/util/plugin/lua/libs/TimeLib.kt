package com.oliverastell.pixeleditor.util.plugin.lua.libs

import com.oliverastell.pixeleditor.util.plugin.lua.helper.FunctionHelper
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.OneArgFunction
import org.luaj.vm2.lib.TwoArgFunction
import org.luaj.vm2.lib.ZeroArgFunction
import kotlin.time.measureTime

class TimeLib : TwoArgFunction() {
    override fun call(
        modname: LuaValue,
        env: LuaValue
    ): LuaValue {
        val time = LuaTable()
        val globals = env.checkglobals()

        time.set("milliseconds", milliseconds)
        time.set("millis", milliseconds)
        time.set("seconds", seconds)
        time.set("measure", measure)

        globals.set("time", time)
        return time
    }

    companion object {
        val milliseconds = FunctionHelper.function {
            valueOf(System.currentTimeMillis().toDouble())
        }

        val seconds = FunctionHelper.function {
            valueOf((System.currentTimeMillis() / 1000).toDouble())
        }

        val measure = FunctionHelper.function { args ->
            val closure = args.checkclosure(1)
            val duration = measureTime {
                closure.invoke()
            }

            val durationTable = LuaTable()
            durationTable.set("seconds", valueOf(duration.inWholeSeconds.toDouble()))
            durationTable.set("millis", valueOf(duration.inWholeMilliseconds.toDouble()))
            durationTable.set("milliseconds", valueOf(duration.inWholeMilliseconds.toDouble()))

            durationTable
        }
    }
}