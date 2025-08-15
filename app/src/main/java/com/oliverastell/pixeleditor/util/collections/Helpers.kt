package com.oliverastell.pixeleditor.util.collections

import androidx.annotation.IntRange
import androidx.compose.runtime.mutableStateListOf
import kotlin.experimental.and
import kotlin.experimental.inv
import kotlin.experimental.or


fun Byte.getBit(@IntRange(from=0,to=7) subBitIndex: Int): Boolean {
    val mask = when(subBitIndex) {
        0 ->    0b10000000.toByte()
        1 ->    0b01000000.toByte()
        2 ->    0b00100000.toByte()
        3 ->    0b00010000.toByte()
        4 ->    0b00001000.toByte()
        5 ->    0b00000100.toByte()
        6 ->    0b00000010.toByte()
        else -> 0b00000001.toByte()
    }

    return this and mask != 0.toByte()
}

fun Byte.withBit(@IntRange(from=0,to=7) subBitIndex: Int, bit: Boolean): Byte {
    var byte = this

    if (!bit)
        byte = byte.inv()

    val mask = when(subBitIndex) {
        0 ->    0b10000000.toByte() // -128
        1 ->    0b01000000.toByte() // 64
        2 ->    0b00100000.toByte() // 32
        3 ->    0b00010000.toByte() // 16
        4 ->    0b00001000.toByte() // 8
        5 ->    0b00000100.toByte() // 4
        6 ->    0b00000010.toByte() // 2
        else -> 0b00000001.toByte() // 1
    }

    byte = byte or mask

    if (!bit)
        byte = byte.inv()

    return byte
}

fun bitArrayOf(vararg bits: Boolean): BitArray {
    val bitArray = BitArray(bits.size)

    for ((index, bit) in bits.withIndex()) {
        bitArray[index] = bit
    }

    return bitArray
}

fun bitArrayOf(bits: String, truthy: Char = '1', falsey: Char = '0'): BitArray {
    val bitArray = BitArray(bits.count { it == truthy || it == falsey })

    var index = 0
    for (char in bits) {
        when (char) {
            truthy -> bitArray[index] = true
            falsey -> bitArray[index] = false
            else -> index--
        }
        index++
    }

    return bitArray
}