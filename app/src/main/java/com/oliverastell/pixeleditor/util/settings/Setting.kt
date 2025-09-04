package com.oliverastell.pixeleditor.util.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

interface Setting<T : Any> {
    var rawValue: T

    fun get() = rawValue

    /**
     * Validates (and potentially converts) value to fit into setting.
     * Null values will always be ignored.
     */
    fun set(to: Any?) {
        if (to == null)
            return

        val converted = convert(to)

        if (converted == null)
            return

        rawValue = constrain(converted)
    }

    /**
     * Converts the given value into the appropriate type for this setting,
     * or returns null if conversion is impossible.
     */
    fun convert(value: Any): T?

    /**
     * Constrain a value to fit the setting, for example constrain a clamped
     * setting to a specific range, so it does not reside outside of the range.
     */
    fun constrain(value: T): T

    companion object {
        fun number(default: Double) = object : NumberSetting {
            override var rawValue by mutableDoubleStateOf(default)

            override fun constrain(value: Double) = value
        }

        fun wholeNumber(default: Int) = object : NumberSetting {
            override var rawValue by mutableDoubleStateOf(default.toDouble())

            override fun constrain(value: Double) = value
        }

        fun clampedNumber(default: Double, range: ClosedFloatingPointRange<Double>) = object : ClampedNumberSetting {
            override var rawValue by mutableDoubleStateOf(default)
            override val range = range

            override fun constrain(value: Double) = value.coerceIn(range)
        }

        fun clampedWholeNumber(default: Int, range: IntRange) = object : ClampedWholeNumberSetting {
            override var rawValue by mutableDoubleStateOf(default.toDouble())
            override val range = range.start.toDouble()..range.endInclusive.toDouble()

            override fun constrain(value: Double) = value.coerceIn(this.range)
        }

        fun color(default: Color) = object : ColorSetting {
            override var rawValue by mutableStateOf(default)

            override fun constrain(value: Color) = value
        }
    }
}