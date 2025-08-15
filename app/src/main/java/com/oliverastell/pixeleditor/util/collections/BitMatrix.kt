package com.oliverastell.pixeleditor.util.collections

import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.experimental.xor

class BitMatrix(val width: Int, val height: Int, val bits: BitArray) {
    constructor(width: Int, height: Int): this(
        width,
        height,
        BitArray(width * height)
    )

    init {
        require(bits.size >= width*height) { "Bytes array must fit width and height" }
    }

    private inline fun mapBytesAgainst(other: BitMatrix, protocol: (Byte, Byte) -> Byte): BitMatrix {
        require(width == other.width && height == other.height) { "Dimensions of bitmask must be identical" }

        val bits = BitArray(bits.size)

        for (i in 0..<bits.byteCount) {
            bits.setByte(i, protocol(this.bits.getByte(i), other.bits.getByte(i)))
        }

        return BitMatrix(width, height, bits)
    }

    infix fun and(other: BitMatrix) = mapBytesAgainst(other) { left, right -> left and right }
    infix fun or(other: BitMatrix) = mapBytesAgainst(other) { left, right -> left or right }
    infix fun xor(other: BitMatrix) = mapBytesAgainst(other) { left, right -> left xor right }

    infix fun nand(other: BitMatrix) = mapBytesAgainst(other) { left, right -> (left and right).inv() }
    infix fun nor(other: BitMatrix) = mapBytesAgainst(other) { left, right -> (left or right).inv() }
    infix fun xnor(other: BitMatrix) = mapBytesAgainst(other) { left, right -> (left xor right).inv() }

    fun fillRegion(x1: Int, y1: Int, x2: Int, y2: Int, bit: Boolean) {
        for (x in x1..<x2)
            for (y in y1..<y2) {
                this[x, y] = bit
            }
    }

    fun xOfIndex(index: Int): Int {
        if (index < 0 || index >= bits.size)
            throw IndexOutOfBoundsException()
        return index % width
    }
    fun yOfIndex(index: Int): Int {
        if (index < 0 || index >= bits.size)
            throw IndexOutOfBoundsException()
        return index / width
    }

    fun indexAt(x: Int, y: Int): Int {
        if (x < 0 || x >= width)
            throw IndexOutOfBoundsException()
        if (y < 0 || y >= height)
            throw IndexOutOfBoundsException()
        return x+y*width
    }

    operator fun get(x: Int, y: Int): Boolean {
        return bits[x+y*width]
    }

    operator fun set(x: Int, y: Int, bit: Boolean) {
        bits[x+y*width] = bit
    }

    override fun toString() = """[${bits.chunked(width) { chunk ->
        chunk.joinToString("") {
            if (it) "1" else "0"
        }
    }.joinToString(", ")}]"""
}