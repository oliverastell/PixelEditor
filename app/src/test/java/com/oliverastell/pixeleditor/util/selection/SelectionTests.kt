package com.oliverastell.pixeleditor.util.selection

import com.oliverastell.pixeleditor.util.Editor
import com.oliverastell.pixeleditor.util.deprecated_vector.Vec2
import org.junit.Assert
import org.junit.Test

class SelectionTests {
    fun expectCount(selection: Selection, expected: Int) {
        var pixelCount = 0

        selection.forEachPixel { x, y -> pixelCount++ }

        Assert.assertEquals(expected, pixelCount)
    }

    @Test
    fun count10x10() {
        val editor = Editor(100, 100)

        val selection = Selection(editor)
//        selection.bitmask.

        selection.add(Shapes.rectangle(
            Vec2(0.0, 0.0),
            Vec2(10.0, 10.0)
        ).toBitmask(editor.width, editor.height))

        expectCount(selection, 100)
    }

    @Test
    fun count10x10add10x10() {
        val editor = Editor(100, 100)

        val selection = Selection(editor)

        selection.add(Shapes.rectangle(
            Vec2(0.0, 0.0),
            Vec2(10.0, 10.0)
        ).toBitmask(editor.width, editor.height))

        selection.add(Shapes.rectangle(
            Vec2(5.0, 5.0),
            Vec2(15.0, 15.0)
        ).toBitmask(editor.width, editor.height))

        expectCount(selection, 175)
    }

    @Test
    fun count10x10invert10x10() {
        val editor = Editor(100, 100)

        val selection = Selection(editor)

        selection.invert(Shapes.rectangle(
            Vec2(0.0, 0.0),
            Vec2(10.0, 10.0)
        ).toBitmask(editor.width, editor.height))

        selection.invert(Shapes.rectangle(
            Vec2(5.0, 5.0),
            Vec2(15.0, 15.0)
        ).toBitmask(editor.width, editor.height))

        expectCount(selection, 150)
    }

}