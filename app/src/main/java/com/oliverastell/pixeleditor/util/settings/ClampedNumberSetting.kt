package com.oliverastell.pixeleditor.util.settings

interface ClampedNumberSetting : NumberSetting {
    val range: ClosedFloatingPointRange<Double>
}