package com.oliverastell.pixeleditor.util.collections

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace
import androidx.compose.ui.graphics.colorspace.ColorSpaces

@OptIn(ExperimentalUnsignedTypes::class)
@JvmInline
value class ColorArray(val backingArray: ULongArray) : Collection<Color> {
    constructor(size: Int, init: Color = Color(0x00000000)) : this(ULongArray(size) { init.value })

    override val size: Int
        get() = backingArray.size

    override fun contains(element: Color) = backingArray.contains(element.value)
    override fun containsAll(elements: Collection<Color>) = elements.all { backingArray.contains(it.value) }
    override fun isEmpty() = backingArray.isEmpty()

    override fun iterator() = object : Iterator<Color> {
        private var index = 0

        override fun hasNext() = index < size

        override fun next(): Color {
            if (!hasNext()) throw NoSuchElementException()

            val color = Color(backingArray[index])
            index++

            return color
        }
    }

    operator fun get(index: Int) = Color(backingArray[index])
    operator fun set(index: Int, color: Color) { backingArray[index] = color.value }
}

fun Color.toULong() = this.value
fun Color.toInt() = (this.convert(ColorSpaces.Srgb).value shr 32).toInt()