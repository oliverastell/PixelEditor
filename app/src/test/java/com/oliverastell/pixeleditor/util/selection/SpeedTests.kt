package com.oliverastell.pixeleditor.util.selection

import com.oliverastell.pixeleditor.util.Editor
import org.junit.Test
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.time.measureTime

class SpeedTests {
    fun <T> time(step: String? = null, action: () -> T): T {
        val result: T
        val duration = measureTime {
            result = action()
        }

        val stackTrace = Thread.currentThread().stackTrace
        val caller = stackTrace[2]

        val callerName = caller.methodName
        val seconds = duration.inWholeMicroseconds / 1000000.0

        val df = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH))
        df.maximumFractionDigits = 340

        if (step != null)
            println("$callerName (step: ${step}) took ${df.format(seconds)}s")
        else
            println("$callerName took ${df.format(seconds)}s")

        return result
    }

    @Test
    fun polygon1000vertices() {
        val editor = Editor(100, 100)

        val selection = Selection(editor)

        val polygon = time("randomPolygon") {
            Shapes.randomPolygon(
                100.0,
                100.0,
                "polygon1000vertices".hashCode(),
                1000
            )
        }

        val polygonBitmask = time("polygon.toBitmask") { polygon.toBitmask(editor.width, editor.height) }

        time("selection.add") { selection.add(polygonBitmask) }

        time("println") { println(selection.bitmask) }
    }

}