package com.oliverastell.pixeleditor.common

import kotlin.math.PI

fun Double.toRadians(): Double = this / 180 * PI
fun Float.toRadians(): Float = this / 180 * PI.toFloat()

fun Double.toDegrees(): Double = this / PI * 180
fun Float.toDegrees(): Float = this / PI.toFloat() * 180

