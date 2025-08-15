//package com.oliverastell.pixeleditor.util.settings
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import kotlin.reflect.KClass
//import kotlin.reflect.safeCast
//
//
//abstract class Setting<T : Any>(private val cls: KClass<T>, default: T) {
//    private var value by mutableStateOf(default)
//
//    fun get() = value
//
//    /**
//     * Validates (and potentially converts) value to fit into setting.
//     * Null values will always be ignored.
//     */
//    fun set(to: Any?) {
//        if (to == null)
//            return
//
//        val converted = convert(to)
//
//        if (converted == null)
//            return
//
//        value = constrain(converted)
//    }
//
//    /**
//     * Converts the given value into the appropriate type for this setting,
//     * or returns null if conversion is impossible.
//     */
//    open fun convert(value: Any): T? = cls.safeCast(value)
//
//    /**
//     * Constrain a value to fit the setting, for example constrain a clamped
//     * setting to a specific range, so it does not reside outside of the range.
//     */
//    open fun constrain(value: T): T = value
//}