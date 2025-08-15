package com.oliverastell.pixeleditor.util.plugin.lua.types

import org.luaj.vm2.LuaClosure
import org.luaj.vm2.LuaFunction
import org.luaj.vm2.LuaInteger
import org.luaj.vm2.LuaNumber
import org.luaj.vm2.LuaString
import org.luaj.vm2.LuaTable
import org.luaj.vm2.LuaThread
import org.luaj.vm2.LuaValue
import org.luaj.vm2.Varargs


// TODO: Implement every member
abstract class LTDeferred : LuaValue() {
    abstract val deferredTo: LuaValue

    override fun add(rhs: LuaValue): LuaValue = deferredTo.add(rhs)
    override fun sub(rhs: LuaValue): LuaValue = deferredTo.sub(rhs)
    override fun mul(rhs: LuaValue): LuaValue = deferredTo.mul(rhs)
    override fun div(rhs: LuaValue): LuaValue = deferredTo.div(rhs)
    override fun pow(rhs: LuaValue): LuaValue = deferredTo.pow(rhs)

    override fun neg(): LuaValue = deferredTo.neg()

    override fun concat(rhs: LuaValue): LuaValue = deferredTo.concat(rhs)
    override fun concatTo(lhs: LuaValue): LuaValue = deferredTo.concatTo(lhs)

    override fun gteq(rhs: LuaValue): LuaValue = deferredTo.gteq(rhs)
    override fun gt(rhs: LuaValue): LuaValue = deferredTo.gt(rhs)
    override fun eq(`val`: LuaValue): LuaValue = deferredTo.eq(`val`)
    override fun lt(rhs: LuaValue): LuaValue = deferredTo.lt(rhs)
    override fun lteq(rhs: LuaValue): LuaValue = deferredTo.lteq(rhs)

    override fun and(rhs: LuaValue): LuaValue = deferredTo.and(rhs)
    override fun or(rhs: LuaValue): LuaValue = deferredTo.or(rhs)

    override fun not(): LuaValue = deferredTo.not()

    override fun invoke(args: Varargs): Varargs = deferredTo.invoke(args)

    override fun type(): Int = deferredTo.type()
    override fun typename(): String? = "(wrapped)"+deferredTo.typename()

    override fun isvalidkey() = deferredTo.isvalidkey()
    override fun isTailcall() = deferredTo.isTailcall

    override fun get(key: LuaValue): LuaValue = deferredTo.get(key)
    override fun rawget(key: LuaValue): LuaValue = deferredTo.rawget(key)

    override fun set(key: LuaValue, value: LuaValue) = deferredTo.set(key, value)
    override fun rawset(key: LuaValue, value: LuaValue) = deferredTo.rawset(key, value)

    override fun getmetatable(): LuaValue? = deferredTo.getmetatable()
    override fun setmetatable(metatable: LuaValue): LuaValue = deferredTo.setmetatable(metatable)

    override fun metatag(tag: LuaValue): LuaValue = deferredTo.metatag(tag)

    // Type checks
    override fun isstring() = deferredTo.isstring()
    override fun isboolean() = deferredTo.isboolean()
    override fun isnil() = deferredTo.isnil()

    override fun isnumber() = deferredTo.isnumber()
    override fun isint() = deferredTo.isint()
    override fun isinttype() = deferredTo.isinttype()
    override fun islong() = deferredTo.islong()

    override fun istable() = deferredTo.istable()

    override fun isclosure() = deferredTo.isclosure()
    override fun isfunction() = deferredTo.isfunction()
    override fun isthread() = deferredTo.isthread()

    override fun isuserdata() = deferredTo.isuserdata()
    override fun isuserdata(c: Class<*>?) = deferredTo.isuserdata(c)

    // Opts
    override fun optstring(defval: LuaString): LuaString? = deferredTo.optstring(defval)
    override fun optjstring(defval: String): String? = deferredTo.optjstring(defval)
    override fun optboolean(defval: Boolean) = deferredTo.optboolean(defval)

    override fun optnumber(defval: LuaNumber?): LuaNumber? = deferredTo.optnumber(defval)
    override fun optint(defval: Int): Int = deferredTo.optint(defval)
    override fun optinteger(defval: LuaInteger): LuaInteger? = deferredTo.optinteger(defval)
    override fun optlong(defval: Long): Long = deferredTo.optlong(defval)

    override fun opttable(defval: LuaTable): LuaTable? = deferredTo.opttable(defval)

    override fun optclosure(defval: LuaClosure): LuaClosure? = deferredTo.optclosure(defval)
    override fun optfunction(defval: LuaFunction): LuaFunction? = deferredTo.optfunction(defval)
    override fun optthread(defval: LuaThread): LuaThread? = deferredTo.optthread(defval)

    override fun optuserdata(c: Class<*>, defval: Any): Any? = deferredTo.optuserdata(c, defval)
    override fun optuserdata(defval: Any): Any? = deferredTo.optuserdata(defval)

    // Type assertions
    override fun checkstring(): LuaString? = deferredTo.checkstring()
    override fun checkboolean(): Boolean = deferredTo.checkboolean()

    override fun checknumber(): LuaNumber? = deferredTo.checknumber()
    override fun checkint(): Int = deferredTo.checkint()
    override fun checkinteger(): LuaInteger? = deferredTo.checkinteger()
    override fun checklong(): Long = deferredTo.checklong()

    override fun checktable(): LuaTable? = deferredTo.checktable()

    override fun checkclosure(): LuaClosure? = deferredTo.checkclosure()
    override fun checkfunction(): LuaFunction? = deferredTo.checkfunction()
    override fun checkthread(): LuaThread? = deferredTo.checkthread()

    override fun checkuserdata(): Any? = deferredTo.checkuserdata()
    override fun checkuserdata(c: Class<*>?): Any? = deferredTo.checkuserdata(c)

    // Casts
    override fun tostring(): LuaValue = deferredTo.tostring()
    override fun tojstring(): String = deferredTo.tojstring()
    override fun toString(): String = deferredTo.toString()
    override fun toboolean(): Boolean = deferredTo.toboolean()

    override fun tonumber(): LuaValue = deferredTo.tonumber()
    override fun tobyte(): Byte = deferredTo.tobyte()
    override fun tochar(): Char = deferredTo.tochar()
    override fun toshort(): Short = deferredTo.toshort()
    override fun toint(): Int = deferredTo.toint()
    override fun tolong(): Long = deferredTo.tolong()
    override fun todouble(): Double = deferredTo.todouble()
    override fun tofloat(): Float = deferredTo.tofloat()

    override fun touserdata(): Any? = deferredTo.touserdata()
    override fun touserdata(c: Class<*>?): Any? = deferredTo.touserdata(c)
}