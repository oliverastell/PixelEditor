package com.oliverastell.pixeleditor.util.vector

import kotlin.math.atan2
import kotlin.math.min
import kotlin.math.max
import kotlin.math.sqrt

data class Vec2i(val x: Int = 0, val y: Int = 0) : Comparable<Vec2i> {
    constructor(xy: Int): this(xy, xy)
    constructor(xy: IntArray): this(xy[0], xy[1])

    val magnitudeSquared: Float
        get() = x.toFloat()*x + y.toFloat()*y

    val manhattan: Int
        get() = x+y

    val magnitude: Double
        get() = sqrt(magnitudeSquared.toDouble())

    val angle: Double
        get() = atan2(y.toDouble(), x.toDouble())

    val slope: Double
        get() = y.toDouble() / x

    fun toVec2i() = Vec2i(x, y)
    fun toVec2() = Vec2(x.toDouble(), y.toDouble())

    fun x(x: Int) = Vec2i(x, y)
    fun y(y: Int) = Vec2i(x, y)

    fun min(that: Vec2i) = Vec2i(min(this.x, that.x), min(this.y, that.y))
    fun max(that: Vec2i) = Vec2i(max(this.x, that.x), max(this.y, that.y))

    operator fun plus(that: Vec2) = Vec2(this.x+that.x, this.y+that.y)
    operator fun plus(that: Vec2i) = Vec2i(this.x+that.x, this.y+that.y)
    operator fun unaryPlus() = Vec2i(x, y)

    operator fun minus(that: Vec2) = Vec2(this.x-that.x, this.y-that.y)
    operator fun minus(that: Vec2i) = Vec2i(this.x-that.x, this.y-that.y)
    operator fun unaryMinus() = Vec2i(-x, -y)

    operator fun times(that: Vec2) = Vec2(this.x*that.x, this.y*that.y)
    operator fun times(that: Vec2i) = Vec2i(this.x*that.x, this.y*that.y)
    operator fun times(that: Int) = Vec2i(this.x*that, this.y*that)
    operator fun times(that: Float) = Vec2(this.x*that.toDouble(), this.y*that.toDouble())
    operator fun times(that: Double) = this*that.toFloat()

    operator fun div(that: Vec2) = Vec2(this.x/that.x, this.y/that.y)
    operator fun div(that: Vec2i) = Vec2i(this.x/that.x, this.y/that.y)
    operator fun div(that: Int) = Vec2i(this.x/that, this.y/that)
    operator fun div(that: Float) = Vec2(this.x/that.toDouble(), this.y/that.toDouble())
    operator fun div(that: Double) = this/that.toFloat()

    operator fun rangeTo(that: Vec2i) = Region(this, that)
    operator fun rangeUntil(that: Vec2i) = Region(this, that - Vec2i(1, 1))

    override fun compareTo(other: Vec2i): Int {
        if (this == other) return 0
        if (this.y > other.y) return 1
        if (this.x > other.x) return 1
        return -1
    }

    override fun toString() = "Vec2i($x, $y)"

    companion object {
        val Zero = Vec2i(0, 0)
        val One = Vec2i(1, 1)
        val Up = Vec2i(0, -1)
        val Down = Vec2i(0, 1)
        val Left = Vec2i(-1, 0)
        val Right = Vec2i(1, 0)
    }
}