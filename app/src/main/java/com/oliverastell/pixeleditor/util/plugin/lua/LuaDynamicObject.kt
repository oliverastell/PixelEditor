package com.oliverastell.pixeleditor.util.plugin.lua

import com.oliverastell.pixeleditor.util.plugin.DynamicObject
//import com.oliverastell.pixeleditor.util.plugin.lua.LuaConverter.toGeneric
//import com.oliverastell.pixeleditor.util.plugin.lua.LuaConverter.toLua
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs
import kotlin.reflect.KClass

class LuaDynamicObject(override val kotlin: LuaValue) : DynamicObject<LuaValue> {
    override val kClass = LuaValue::class

    override fun get(key: DynamicObject<*>): DynamicObject<*> = kotlin.get(key.toLua()).toDynamic()
    override fun set(key: DynamicObject<*>, value: DynamicObject<*>) {
        kotlin.set(key.toLua(), value.toLua())
    }

    override fun invoke(args: List<DynamicObject<*>>): DynamicObject<*> =
        kotlin.invoke(args.map { it.toLua() }.toTypedArray()).arg1().toDynamic()
}