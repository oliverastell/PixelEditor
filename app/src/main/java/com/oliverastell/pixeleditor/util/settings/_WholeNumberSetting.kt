//package com.oliverastell.pixeleditor.util.settings
//
//import androidx.compose.ui.util.fastRoundToInt
//import kotlin.math.floor
//import kotlin.math.roundToInt
//import kotlin.math.truncate
//
//open class WholeNumberSetting(default: Int) : NumberSetting(default.toDouble()) {
//    override fun constrain(value: Double): Double = truncate(value)
//}