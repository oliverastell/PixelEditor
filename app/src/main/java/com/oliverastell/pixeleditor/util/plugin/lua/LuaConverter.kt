package com.oliverastell.pixeleditor.util.plugin.lua

import com.oliverastell.pixeleditor.util.plugin.DynamicObject
import com.oliverastell.pixeleditor.util.plugin.DynamicObjects
import com.oliverastell.pixeleditor.util.plugin.lua.types.LTSandboxed
import org.luaj.vm2.LuaBoolean
import org.luaj.vm2.LuaDouble
import org.luaj.vm2.LuaFunction
import org.luaj.vm2.LuaInteger
import org.luaj.vm2.LuaNil
import org.luaj.vm2.LuaString
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaUserdata
import org.luaj.vm2.LuaValue

fun LuaValue?.toDynamic(): DynamicObject<*> = when (this) {
    null -> DynamicObjects.None
    is LuaNil -> DynamicObjects.None
    is LuaDouble -> DynamicObjects.DynObject(this.todouble())
    is LuaInteger -> DynamicObjects.DynInt(this.toint())
    is LuaString -> DynamicObjects.DynString(this.tojstring())

    else -> LuaDynamicObject(this)
}

fun <T: Any> DynamicObject<T>?.toLua(): LuaValue = when (this) {
    null -> LuaValue.NIL
    is DynamicObjects.None -> LuaValue.NIL
    is DynamicObjects.DynByte -> LuaValue.valueOf(this.kotlin.toInt())
    is DynamicObjects.DynShort -> LuaValue.valueOf(this.kotlin.toInt())
    is DynamicObjects.DynInt -> LuaValue.valueOf(this.kotlin)
    is DynamicObjects.DynLong -> LuaValue.valueOf(this.kotlin.toDouble())
    is DynamicObjects.DynFloat -> LuaValue.valueOf(this.kotlin.toDouble())
    is DynamicObjects.DynDouble -> LuaValue.valueOf(this.kotlin)
    is DynamicObjects.DynChar -> LuaValue.valueOf(this.kotlin.toString())
    is DynamicObjects.DynString -> LuaValue.valueOf(this.kotlin)

    else -> LTSandboxed(this)
}

//object LuaConverter {
//    fun LuaValue.toGeneric(): Any = when (this) {
//        is LuaTable -> LuaDynamicObject(this)
//        is LuaFunction -> LuaDynamicObject(this)
//
//        is LuaNil -> Unit
//        is LuaString -> this.tojstring()
//        is LuaDouble -> this.todouble()
//        is LuaInteger -> this.toint()
//        is LuaUserdata -> this.touserdata()
//        is LuaBoolean -> this.toboolean()
//        else -> this
//    }
//
//    fun Any.toLua(): LuaValue = when (this) {
//        is LuaValue -> this
//
//        is LuaDynamicObject -> this.lua
//
//        // Handle common types
//        is Unit -> LuaValue.NIL
//        is String -> LuaValue.valueOf(this)
//        is ByteArray -> LuaValue.valueOf(this)
//        is Int -> LuaValue.valueOf(this)
//        is Double -> LuaValue.valueOf(this)
//        is Boolean -> LuaValue.valueOf(this)
//        else -> LTSandboxed(this)
//    }
//}