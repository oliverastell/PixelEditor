package com.oliverastell.pixeleditor.util.collections

import androidx.annotation.IntRange
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or
import kotlin.math.ceil
import kotlin.text.iterator

class BitArray(override val size: Int, val backingArray: ByteArray): Collection<Boolean> {
    constructor(size: Int) : this(size, ByteArray(ceil(size / 8.0).toInt()))

    init {
        require(backingArray.size >= size / 8.0) { "Bytes array must fit width and height" }
    }

    val byteCount = backingArray.size

    fun getByte(byteIndex: Int) = backingArray[byteIndex]
    fun setByte(byteIndex: Int, byte: Byte) { backingArray[byteIndex] = byte }

    private fun byteIndexOfBit(bitIndex: Int) = bitIndex / 8
    private fun subBitIndexOf(bitIndex: Int) = bitIndex % 8


    override fun isEmpty(): Boolean {
        return size == 0
    }

    override fun contains(element: Boolean) = backingArray.any { it != 0.toByte() }

    override fun iterator() = object : Iterator<Boolean> {
        var index = 0

        override fun next(): Boolean {
            val bit = get(index)
            index++
            return bit
        }

        override fun hasNext(): Boolean = index < size
    }

    override fun containsAll(elements: Collection<Boolean>) = elements.all { element -> contains(element) }

    operator fun get(bitIndex: Int): Boolean {
        if (bitIndex < 0 || bitIndex >= size)
            throw IndexOutOfBoundsException()

        val byteIndex = byteIndexOfBit(bitIndex)
        val subBitIndex = subBitIndexOf(bitIndex)
        return getByte(byteIndex).getBit(subBitIndex)
    }

    operator fun set(bitIndex: Int, bit: Boolean) {
        if (bitIndex < 0 || bitIndex >= size)
            throw IndexOutOfBoundsException()

        val byteIndex = byteIndexOfBit(bitIndex)
        val subBitIndex = subBitIndexOf(bitIndex)

        return setByte(byteIndex, getByte(byteIndex).withBit(subBitIndex, bit))
    }

    override fun toString() = this.joinToString("") { if (it) "1" else "0" }
}

