package com.oliverastell.pixeleditor.util.settings

interface NumberSetting : Setting<Double> {
    override fun convert(value: Any): Double? {
        if (value is Double)
            return value

        if (value is Number)
            return value.toDouble()

        if (value is String)
            return value.toDoubleOrNull()

        return null
    }
}