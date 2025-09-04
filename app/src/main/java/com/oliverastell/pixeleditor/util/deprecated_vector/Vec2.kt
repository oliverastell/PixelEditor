package com.oliverastell.pixeleditor.util.deprecated_vector

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.max
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt

data class Vec2(val x: Double = 0.0, val y: Double = 0.0) : Comparable<Vec2> {
    constructor(xy: Double): this(xy, xy)
    constructor(xy: Float): this(xy.toDouble(), xy.toDouble())
    constructor(xy: Int): this(xy.toDouble())

    constructor(x: Float = 0f, y: Float = 0f): this(x.toDouble(), y.toDouble())
    constructor(x: Int = 0, y: Int = 0): this(x.toDouble(), y.toDouble())

    constructor(xy: DoubleArray): this(xy[0], xy[1])
    constructor(xy: FloatArray): this(xy[0], xy[1])
    constructor(xy: IntArray): this(xy[0], xy[1])

    val manhattan: Double
        get() = x+y

    val magnitudeSquared: Double
        get() = x*x + y*y

    val magnitude: Double
        get() = sqrt(magnitudeSquared)

    val angle: Double
        get() = atan2(y, x)

    val slope: Double
        get() = y / x

    fun toVec2i() = Vec2i(x.toInt(), y.toInt())
    fun toVec2() = Vec2(x, y)

    fun roundToVec2i() = Vec2i(round(x).toInt(), round(y).toInt())
    fun roundToCenter() = floor(this) + Vec2(0.5, 0.5)

    fun x(x: Double) = Vec2(x, y)
    fun y(y: Double) = Vec2(x, y)

    fun min(that: Vec2) = Vec2(min(this.x, that.x), min(this.y, that.y))
    fun max(that: Vec2) = Vec2(max(this.x, that.x), max(this.y, that.y))

    operator fun plus(that: Vec2) = Vec2(this.x+that.x, this.y+that.y)
    operator fun plus(that: Vec2i) = Vec2(this.x+that.x, this.y+that.y)
    operator fun unaryPlus() = Vec2(x, y)

    operator fun minus(that: Vec2) = Vec2(this.x-that.x, this.y-that.y)
    operator fun minus(that: Vec2i) = Vec2(this.x-that.x, this.y-that.y)
    operator fun unaryMinus() = Vec2(-x, -y)

    operator fun times(that: Vec2) = Vec2(this.x*that.x, this.y*that.y)
    operator fun times(that: Vec2i) = Vec2(this.x*that.x, this.y*that.y)
    operator fun times(that: Int) = Vec2(this.x*that, this.y*that)
    operator fun times(that: Float) = Vec2(this.x*that, this.y*that)
    operator fun times(that: Double) = this*that.toFloat()

    operator fun div(that: Vec2) = Vec2(this.x/that.x, this.y/that.y)
    operator fun div(that: Vec2i) = Vec2(this.x/that.x, this.y/that.y)
    operator fun div(that: Int) = Vec2(this.x/that, this.y/that)
    operator fun div(that: Float) = Vec2(this.x/that, this.y/that)
    operator fun div(that: Double) = this/that.toFloat()

    override fun compareTo(other: Vec2): Int {
        if (this == other) return 0
        if (this.y > other.y) return 1
        if (this.x > other.x) return 1
        return -1
    }

    override fun toString() = "Vec2($x, $y)"

    companion object {
        val Zero = Vec2(0, 0)
        val One = Vec2(1, 1)
        val Up = Vec2(0, -1)
        val Down = Vec2(0, 1)
        val Left = Vec2(-1, 0)
        val Right = Vec2(1, 0)

        fun fromAngle(angle: Double) = Vec2(cos(angle), sin(angle))
    }
}