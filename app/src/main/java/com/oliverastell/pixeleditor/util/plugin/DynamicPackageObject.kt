package com.oliverastell.pixeleditor.util.plugin

import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

class DynamicPackageObject : DynamicObject<Map<String, DynamicObject<*>>> {
    override val kotlin = mutableMapOf<String, DynamicObject<*>>()

    override fun get(key: DynamicObject<*>): DynamicObject<*> {
        if (!key.kClass.isSuperclassOf(String::class))
            return DynamicObjects.None

        val key = key.kotlin as String
        return kotlin[key] ?: DynamicObjects.None
    }

    override fun set(key: DynamicObject<*>, value: DynamicObject<*>) {
        error("Cannot modify package dynamically")
    }

    operator fun set(key: String, value: DynamicObject<*>) {
        kotlin[key] = value
    }
}