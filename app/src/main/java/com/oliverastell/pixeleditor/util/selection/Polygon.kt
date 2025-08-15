package com.oliverastell.pixeleditor.util.selection

import androidx.collection.MutableDoubleList
import androidx.collection.mutableDoubleListOf
import com.oliverastell.pixeleditor.util.Editor
import com.oliverastell.pixeleditor.util.collections.BitMatrix
import com.oliverastell.pixeleditor.util.vector.Vec2
import com.oliverastell.pixeleditor.util.vector.ceil
import com.oliverastell.pixeleditor.util.vector.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class PolygonRegion(val points: List<Vec2>) {
    constructor(vararg points: Vec2): this(points.toList())

    init {
        require(points.isNotEmpty()) { "Polygon requires points" }
    }

    val boundary by lazy {
        if (points.isEmpty())
            throw UnsupportedOperationException()

        var minX = points[0].x
        var minY = points[0].y
        var maxX = points[0].x
        var maxY = points[0].y

        for (i in 1..<points.size) {
            val point = points[i]

            minX = min(minX, point.x)
            minY = min(minY, point.y)

            maxX = max(maxX, point.x)
            maxY = max(maxY, point.y)
        }

        floor(
            Vec2(minX, minY)
        ).toVec2i()..ceil(
            Vec2(maxX, maxY)
        ).toVec2i()
    }

    // TODO: Refactor this to be like, super duper sensitive I guess.
    inline fun forEachPixel(action: (x: Int, y: Int) -> Unit) {
//////         Calculates the boundary of the shape, then, for each row, uses a point-in-polygon algorithm
//////           to run the action for each pixel.

        val offset = boundary.range.start
        val scanLines = arrayOfNulls<MutableDoubleList>(boundary.height)

        // Calculate intersections on each scanline.
        for (i in points.indices) {
            val nextI = (i + 1) % points.size

            val pointAX = points[i].x
            val pointAY = points[i].y
            val pointBX = points[nextI].x
            val pointBY = points[nextI].y

            // Ignore horizontal lines. Horizontal lines intersect scan lines infinitely, causing
            //   issues for the algorithm.
            if (pointAY == pointBY)
                continue

            val minY = min(pointAY, pointBY)
            val maxY = max(pointAY, pointBY)

            val verticalSpan = (round(minY).toInt())..<(round(maxY).toInt())

            for (y in verticalSpan) {
                val yCentered = y+0.5

                val scanLineIndex = y-offset

                if (scanLines[scanLineIndex] == null)
                    scanLines[scanLineIndex] = mutableDoubleListOf()

                val anchorX = pointAX
                val anchorY = pointAY

                val edgeVectorX = pointBX - anchorX
                val edgeVectorY = pointBY - anchorY

                val displacementFromEdgeY = yCentered - anchorY
                val inverseSlope = edgeVectorX / edgeVectorY

                // y = mx   ->   x = y * (1/m)
                val displacementFromEdgeX = displacementFromEdgeY * inverseSlope

                scanLines[scanLineIndex]?.add(displacementFromEdgeX+anchorX)
            }
        }

        for (x in boundary.domain) {
            for (y in boundary.range) {
                val scanLine = scanLines[y-offset]
                val pointsToTheRight = scanLine?.count { x+.5 < it } ?: 0

                if (pointsToTheRight % 2 == 1)
                    action.invoke(x, y)
            }
        }
    }

    fun toBitmask(editor: Editor) = toBitmask(editor.width, editor.height)

    fun toBitmask(width: Int, height: Int): BitMatrix {
        val bitmask = BitMatrix(width, height)

        forEachPixel { x, y ->
            if (x < 0 || x >= bitmask.width || y < 0 || y >= bitmask.height)
                return@forEachPixel

            bitmask[x, y] = true
        }

        return bitmask
    }
}