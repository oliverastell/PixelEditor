package com.oliverastell.pixeleditor.util.plugin.lua.helper

import org.luaj.vm2.LuaFunction
import org.luaj.vm2.Varargs
import org.luaj.vm2.lib.VarArgFunction

open class KLuaFunction(val protocol: (args: Varargs) -> Varargs) : VarArgFunction() {
    override fun invoke(args: Varargs): Varargs = protocol(args)
}

object FunctionHelper {
    fun function(protocol: (args: Varargs) -> Varargs): LuaFunction = KLuaFunction(protocol)
}