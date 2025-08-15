package com.oliverastell.pixeleditor.util.plugin.lua.types

import com.oliverastell.pixeleditor.util.plugin.DynamicObject
import com.oliverastell.pixeleditor.util.plugin.lua.toDynamic
import com.oliverastell.pixeleditor.util.plugin.lua.toLua
import org.luaj.vm2.LuaUserdata
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs

//import com.oliverastell.pixeleditor.util.nothing
//import com.oliverastell.pixeleditor.util.plugin.lua.LuaConverter.toGeneric
//import com.oliverastell.pixeleditor.util.plugin.lua.LuaConverter.toLua
//import com.oliverastell.pixeleditor.util.plugin.Sandboxed
//import org.luaj.vm2.LuaUserdata
//import org.luaj.vm2.LuaValue
//import org.luaj.vm2.Varargs
//import java.lang.reflect.Method
//import kotlin.reflect.KClass
//import kotlin.reflect.KFunction
//import kotlin.reflect.KMutableProperty
//import kotlin.reflect.KMutableProperty1
//import kotlin.reflect.KProperty
//import kotlin.reflect.KProperty1
//import kotlin.reflect.full.hasAnnotation
//import kotlin.reflect.full.memberFunctions
//import kotlin.reflect.full.memberProperties
//

class LTSandboxed<T: Any>(val obj: DynamicObject<T>) : LuaUserdata(obj) {
    override fun get(key: LuaValue): LuaValue = obj[key.toDynamic()].toLua()
    override fun set(key: LuaValue, value: LuaValue) {
        obj[key.toDynamic()] = value.toDynamic()
    }
    override fun invoke(args: Varargs): Varargs =
        obj.invoke(List(args.narg()) { index ->
            args.arg(index+1).toDynamic()
        }).toLua()
}
