package com.oliverastell.pixeleditor.util.plugin.lua.types

import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaValue

// stole this straight from the docs im too lazy to code it myself. :P
class LTReadOnlyTable(table: LuaValue) : LuaTable() {
    init {
        presize(table.length(), 0)
        var n = table.next(NIL)
        while (!n.arg1().isnil()) {
            val key = n.arg1()
            val value = n.arg(2)
            super.rawset(key, if (value.istable()) LTReadOnlyTable(value) else value)
            n = table
                .next(n.arg1())
        }
    }

    override fun setmetatable(metatable: LuaValue): LuaValue {
        return error("table is read-only")
    }

    override fun set(key: Int, value: LuaValue) {
        error("table is read-only")
    }

    override fun rawset(key: Int, value: LuaValue) {
        error("table is read-only")
    }

    override fun rawset(key: LuaValue, value: LuaValue) {
        error("table is read-only")
    }

    override fun remove(pos: Int): LuaValue {
        return error("table is read-only")
    }
}