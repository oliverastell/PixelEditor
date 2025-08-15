package com.oliverastell.pixeleditor.common


fun lerp(a: Double, b: Double, time: Double) = (b - a) * time + a
fun lerp(a: Float, b: Float, time: Float) = (b - a) * time + a
fun lerp(a: Int, b: Int, time: Int) = (b - a) * time + a

fun lerp(range: ClosedFloatingPointRange<Double>, time: Double) = lerp(range.start, range.endInclusive, time)
fun lerp(range: ClosedFloatingPointRange<Float>, time: Float) = lerp(range.start, range.endInclusive, time)
fun lerp(range: IntRange, time: Int) = lerp(range.start, range.endInclusive, time)



fun invlerp(a: Double, b: Double, value: Double) = (value - a) / (b - a)
fun invlerp(a: Float, b: Float, value: Float) = (value - a) / (b - a)
fun invlerp(a: Int, b: Int, value: Int) = (value - a) / (b - a)

fun invlerp(range: ClosedFloatingPointRange<Double>, value: Double) = invlerp(range.start, range.endInclusive, value)
fun invlerp(range: ClosedFloatingPointRange<Float>, value: Float) = invlerp(range.start, range.endInclusive, value)
fun invlerp(range: IntRange, value: Int) = invlerp(range.start, range.endInclusive, value)