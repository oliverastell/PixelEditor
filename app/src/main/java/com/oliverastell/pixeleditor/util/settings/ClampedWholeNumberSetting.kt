package com.oliverastell.pixeleditor.util.settings

interface ClampedWholeNumberSetting : ClampedNumberSetting, WholeNumberSetting {
    val intRange: IntRange
        get() = range.start.toInt()..range.endInclusive.toInt()
}