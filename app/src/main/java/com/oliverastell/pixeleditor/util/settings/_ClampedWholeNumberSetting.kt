//package com.oliverastell.pixeleditor.util.settings
//
//class ClampedWholeNumberSetting(
//    default: Int,
//    val range: IntRange
//) : WholeNumberSetting(default) {
//    override fun constrain(value: Double): Double = value.toInt().coerceIn(range).toDouble()
//}