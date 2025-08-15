package com.oliverastell.pixeleditor.util.selection

import com.oliverastell.pixeleditor.util.collections.BitArray
import com.oliverastell.pixeleditor.util.collections.BitMatrix
import com.oliverastell.pixeleditor.util.collections.bitArrayOf
import org.junit.Assert
import org.junit.Test

//import com.oliverastell.pixeleditor.util.selection.

class BitmaskTests {
    fun assertBitArrayEquals(expecteds: BitArray, actuals: BitArray) = assertBitArrayEquals(null, expecteds, actuals)
    fun assertBitArrayEquals(message: String? = null, expecteds: BitArray, actuals: BitArray) {
        for (i in expecteds.indices) {
            if (expecteds[i] != actuals[i])
                throw AssertionError("${message ?: ""}\nExpected : $expecteds\nActual   : $actuals")
        }
    }


    @Test
    fun assignment() {
        val bitArray = BitArray(8)

        bitArray[0] = true
        bitArray[2] = true
        bitArray[5] = true
        bitArray[7] = true

        Assert.assertEquals("10100101", bitArray.toString())
    }

    @Test
    fun fill() {
        val bitmask = BitMatrix(5, 5)
        bitmask.fillRegion(1, 1, 4, 4, true)

        assertBitArrayEquals(bitArrayOf("00000 01110 01110 01110 00000"), bitmask.bits)
    }
}