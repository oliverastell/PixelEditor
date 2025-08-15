package com.oliverastell.pixeleditor.util.selection

import com.oliverastell.pixeleditor.util.Editor
import com.oliverastell.pixeleditor.util.collections.BitMatrix


class Selection(val editor: Editor) {
    constructor(selection: Selection): this(selection.editor) {
        this.bitmask = selection.bitmask
    }

    var bitmask = BitMatrix(editor.width, editor.height)

    fun add(other: BitMatrix) {
        bitmask = bitmask or other
    }

    fun invert(other: BitMatrix) {
        bitmask = bitmask xor other
    }

    fun add(other: Selection) = add(other.bitmask)
    fun invert(other: Selection) = invert(other.bitmask)

    fun clear() {
        bitmask = BitMatrix(editor.width, editor.height)
    }

    inline fun forEachPixel(protocol: (x: Int, y: Int) -> Unit) {
        for ((index, bit) in bitmask.bits.withIndex()) {
            if (bit) {
                protocol(bitmask.xOfIndex(index), bitmask.yOfIndex(index))
            }
        }
    }
}