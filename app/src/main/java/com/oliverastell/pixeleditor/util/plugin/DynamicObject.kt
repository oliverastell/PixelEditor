package com.oliverastell.pixeleditor.util.plugin

import com.oliverastell.pixeleditor.util.Identifier
import com.oliverastell.pixeleditor.util.plugin.lua.LuaServer
import com.oliverastell.pixeleditor.util.plugin.Sandboxed
import com.oliverastell.pixeleditor.util.plugin.lua.libs.PluginLib
import com.oliverastell.pixeleditor.util.plugin.lua.toDynamic
import java.nio.file.Path
import kotlin.io.path.extension
import kotlin.io.path.readText
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties

object DynamicObjects {
    fun Any?.dynamic() = when (this) {
        null -> None

        is DynamicObject<*> -> error("Dynamic object being casted to dynamic object indicates a lapse in logic somewhere")

        is Unit -> None
        is Byte -> DynByte(this)
        is Short -> DynShort(this)
        is Int -> DynInt(this)
        is Long -> DynLong(this)
        is Float -> DynFloat(this)
        is Double -> DynDouble(this)
        is String -> DynString(this)
        is Char -> DynChar(this)

        else -> DynObject(this)
    }

    object None : DynamicObject<Unit> {
        override val kotlin = Unit
    }

    class DynByte(override val kotlin: Byte) : DynamicObject<Byte>
    class DynShort(override val kotlin: Short) : DynamicObject<Short>
    class DynInt(override val kotlin: Int) : DynamicObject<Int>
    class DynLong(override val kotlin: Long) : DynamicObject<Long>
    class DynFloat(override val kotlin: Float) : DynamicObject<Float>
    class DynDouble(override val kotlin: Double) : DynamicObject<Double>
    class DynString(override val kotlin: String) : DynamicObject<String>
    class DynChar(override val kotlin: Char) : DynamicObject<Char>
    class DynObject<T: Any>(override val kotlin: T) : DynamicObject<T> {
        override fun get(key: DynamicObject<*>): DynamicObject<*> {
            if (key.kClass.isSuperclassOf(String::class))
                return None

            val key = key.kotlin as String

            val property = kClass.memberProperties.find { it.name == key && it.hasAnnotation<Sandboxed>() } as KProperty1<T, *>?
            if (property != null)
                return property.get(kotlin)?.dynamic() ?: None

            val functions = kClass.memberFunctions.filter { it.name == key && it.hasAnnotation<Sandboxed>() }
            if (functions.size > 1)
                error("Conflicting overloads")
            else if (functions.size == 1)
                return functions.first().dynamic()

            return None
        }

        override fun set(key: DynamicObject<*>, value: DynamicObject<*>) {
            if (key.kClass.isSuperclassOf(String::class))
                return

            val key = key.kotlin as String

            val property = kClass.memberProperties.find { it.name == key && it.hasAnnotation<Sandboxed>() } as KProperty1<T, *>?

            if (property == null || property !is KMutableProperty1<T, *>)
                error("Cannot assign to non mutable property")

            property.setter.call(kotlin, value.kotlin)
        }

        override fun invoke(args: List<DynamicObject<*>>): DynamicObject<*> {
            val args = args.map {
                it.kotlin
            }

            val invoker = kClass.memberFunctions.find { it.name == "invoke" }
            if (invoker == null)
                error("Cannot invoke object")

            return invoker.call(args)?.dynamic() ?: None
        }
    }
}

interface DynamicObject<T : Any> {
    val kotlin: T
    val kClass: KClass<out T>
        get() = kotlin::class

    operator fun get(key: DynamicObject<*>): DynamicObject<*> = DynamicObjects.None
    operator fun set(key: DynamicObject<*>, value: DynamicObject<*>) {}
    operator fun invoke(args: List<DynamicObject<*>>): DynamicObject<*> = DynamicObjects.None

    companion object {
        val supportedFormats = mutableMapOf<String, (plugin: Plugin, identifier: Identifier, path: Path) -> DynamicObject<*>>()

        init {
            setProtocol("lua") { plugin, identifier, path ->
                return@setProtocol LuaServer.runSandbox(PluginLib(plugin), path.readText(), identifier.toString()).arg1().toDynamic()
            }
        }

        fun setProtocol(format: String, protocol: (plugin: Plugin, identifier: Identifier, path: Path) -> DynamicObject<*>) {
            supportedFormats[format] = protocol
        }

        fun supports(format: String) = supportedFormats.containsKey(format)

        fun fromPath(plugin: Plugin, identifier: Identifier, path: Path) =
            supportedFormats[path.extension]?.invoke(plugin, identifier, path) ?: DynamicObjects.None

    }
}